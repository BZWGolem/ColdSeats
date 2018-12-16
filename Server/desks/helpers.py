from common.models import database, Desks, Users, Reservations, Floors, Buildings
import logging
import datetime

logger = logging.getLogger('peewee')
logger.addHandler(logging.StreamHandler())
logger.setLevel(logging.DEBUG)


def get_desk_data(id_desk, date, token):
    if token:
        try:
            user = Users.select().where(Users.token == token).get()
        except Users.DoesNotExist:
            return {'status': 'Wrong user token', 'code': 'E012'}

    if date == '':
        need_date = datetime.datetime.now().strftime("%Y%m%d")
    else:
        need_date = date

    try:
        desk = (Desks.select(Desks, Floors)
                     .join(Floors, on=((Desks.number == Floors.number) & (Desks.id_building == Floors.id_building)))
                     .where(Desks.id_desk == id_desk).get())
    except Desks.DoesNotExist:
        return {'status': 'No Desk found', 'code': 'E005'}
    
    desk_cursor = database.execute_sql("""SELECT id_desk, d.number, b.name FROM desks d
                         JOIN floors f ON d.number = f.number AND d.id_building = f.id_building
                         JOIN buildings b ON f.id_building = b.id_building
                         WHERE d.id_desk = %s LIMIT %s OFFSET %s""", [id_desk, 1, 0])
    desk = desk_cursor.fetchall()[0]
  
    try:
        reservation = (Reservations.select()
                                   .where(Reservations.id_desk == desk[0])
                                   .where(Reservations.timestamp == need_date)
                                   .get())
        if token:
            if reservation.id_user == user:
                reservation_dict = {'status': "MY_{}".format(reservation.status), 'username': reservation.id_user.username}
            else:
                reservation_dict = {'status': reservation.status, 'username': reservation.id_user.username}
        else:
            reservation_dict = {'status': reservation.status, 'username': reservation.id_user.username}
    except Reservations.DoesNotExist:
        reservation_dict = {'status': 'FREE', 'username': ''}

    return {'building_name': desk[2],
            'floor_name': desk[1],
            'id_desk': desk[0],
            'date': need_date,
            'status': reservation_dict['status'],
            'username':  reservation_dict['username']}


def create_desk(id_building, number, nfc_id):
    desk = Desks.create(id_building=id_building,
                        number=number,
                        nfc_id=nfc_id,
                        status='FREE')
    return {'status': 'Created desk', 'id': desk.id_desk}


def desk_confirm(nfc_id, token):
    try:
        user = Users.select().where(Users.token == token).get()
    except Users.DoesNotExist:
        return {'status': 'Wrong user token', 'code': 'E012'}
    date = datetime.datetime.now().strftime("%Y%m%d")
    desk = Desks.select().where(Desks.nfc_id == nfc_id).get()

    # check if reservation for user exists
    try:
        reserve = (Reservations.select()
                               .where(Reservations.id_desk == desk)
                               .where(Reservations.timestamp == date)
                               .get())

        if reserve.id_user == user:
            (Reservations.update(status='TAKEN')
                         .where(Reservations.id_desk == desk)
                         .where(Reservations.timestamp == date)
                         .where(Reservations.id_user == user)
                         .execute())
        else:
            return {'status': 'Cannot take', 'id_desk': desk.id_desk, 'code': 'E011'}
    except Reservations.DoesNotExist:
        rsv_count = (Reservations.select()
                                 .where(Reservations.timestamp == date)
                                 .where(Reservations.id_user == user)
                                 .count())
        if rsv_count == 0:
            Reservations.create(id_user=user,
                                id_desk=desk,
                                timestamp=date,
                                status='TAKEN')
        else:
            return {'status': 'You have already reservation for today', 'code': 'W002'}
    return {'status': 'Taken', 'id_desk': desk.id_desk}


def desk_reserve(id_desk, date, token):
    try:
        user = Users.select().where(Users.token == token).get()
    except Users.DoesNotExist:
        return {'status': 'Wrong user token', 'code': 'E012'}

    # check if reservation exists
    reserve = (Reservations.select()
                           .where(Reservations.id_desk == id_desk)
                           .where(Reservations.timestamp == date)
                           .where(Reservations.id_user != user))
    if reserve.exists():
        return {'status': 'Cannot reserve', 'id_desk': id_desk, 'code': 'E010'}
    
    rsv_count = (Reservations.select()
                             .where(Reservations.timestamp == date)
                             .where(Reservations.id_user == user)
                             .count())
    if rsv_count == 0:
        Reservations.get_or_create(id_user=user,
                                   id_desk=id_desk,
                                   timestamp=date,
                                   status='RESERVED')
    else:
        return {'status': 'You have already reservation for {}'.format(date), 'code': 'W003'}
    return {'status': 'Reserved', 'id_desk': id_desk}


def delete_reservation(token, date):
    if date == '':
        need_date = datetime.datetime.now().strftime("%Y%m%d")
    else:
        need_date = date

    try:
        user = Users.select().where(Users.token == token).get()
    except Users.DoesNotExist:
        return {'status': 'Wrong user token', 'code': 'E012'}

    reservation_query = (Reservations.select()
                                     .where(Reservations.id_user == user)
                                     .where((Reservations.status == 'TAKEN') | (Reservations.status == 'RESERVED'))
                                     .where(Reservations.timestamp == need_date))
    if reservation_query.exists():
        for reservation in reservation_query:
            Reservations.delete().where(Reservations.id_reservation == reservation).execute()
        return {'status': 'Deleted reservation', 'date': date, 'user': user.username}
    else:
        return {'status': 'No reservation', 'date': date, 'user': user.username, 'code': 'W001'}