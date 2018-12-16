from common.models import Buildings, Floors, Desks, Reservations, Users
import datetime
from peewee import JOIN


def get_floor_data(building_name, number, date, token):
    try:
        user = Users.select().where(Users.token == token).get()
    except Users.DoesNotExist:
        return {'status': 'Wrong user token', 'code': 'E012'}

    if date == '':
        need_date = datetime.datetime.now().strftime("%Y%m%d")
    else:
        need_date = date
    try:
        building = (Buildings.select()
                             .where(Buildings.name == building_name)
                             .get())
    except Buildings.DoesNotExist:
        return {'status': 'No Building found', 'code': 'E005'}

    try:
        floor = (Floors.select()
                       .where(Floors.id_building == building.id_building)
                       .where(Floors.number == number)
                       .get())
    except Floors.DoesNotExist:
        return {'status': 'No Floor found', 'code': 'E005'}
    
    try:
        desk = (Desks.select()
                     .where(Desks.id_building == building.id_building)
                     .where(Desks.number == number))
        desk_list = [{'id_desk': desk_data.id_desk, 'status': 'FREE'} for desk_data in desk]
    except Floors.DoesNotExist:
        desk_list = []

    # get reservation data
    for index, desk in enumerate(desk_list):
        reservation_query = (Reservations.select()
                                         .where(Reservations.id_desk == desk['id_desk'])
                                         .where(Reservations.timestamp == need_date))
        if reservation_query.exists():
            reserv = reservation_query.get()
            desk_list[index]['status'] = reserv.status
            if reserv.id_user == user:
                if reserv.status == 'RESERVED':
                    desk_list[index]['status'] = 'MY_RESERVED'
                elif reserv.status == 'TAKEN':
                    desk_list[index]['status'] = 'MY_TAKEN'

    row_str_list = floor.shape.split(';')
    column_str_list = row_str_list[0].split(',')

    return_shape = floor.shape.replace(';', ',')

    return {'shape': return_shape, 'desks_id': desk_list, 'width': len(column_str_list), 'height': len(row_str_list)}


def create_floor(id_building, number, shape):
    floor, created = Floors.get_or_create(id_building=id_building,
                                          number=number,
                                          defaults={'shape': shape})
    if not created:
        return {'status': 'Floor exist', 'code': 'W001'}
    return {'status': 'Floor created'}


def create_object(id_building, number, id_object, pos_x, pos_y):
    try:
        floor = (Floors.select()
                       .where(Floors.id_building == id_building)
                       .where(Floors.number == number)
                       .get())
    except Floors.DoesNotExist:
        return {'status': 'No Floor found', 'code': 'E005'}
    
    shape = floor.shape
    row_str_list = shape.split(';')

    if len(row_str_list) < pos_y:
        return {'status': 'Wrong coordinates', 'code': 'W002'}

    column_str_list = row_str_list[pos_y].split(',')

    if len(column_str_list) < pos_x:
        return {'status': 'Wrong coordinates', 'code': 'W002'}
    
    column_str_list[pos_x] = str(id_object)
    column_str = ','.join(column_str_list)
    row_str_list[pos_y] = column_str
    shape = ';'.join(row_str_list)

    (Floors.update(shape=shape)
           .where(Floors.id_building == id_building)
           .where(Floors.number == number)
           .execute())
    return {'status': 'Added object to floor'}


def find_me(token):
    try:
        user = Users.select().where(Users.token == token).get()
    except Users.DoesNotExist:
        return {'status': 'Wrong user token', 'code': 'E012'}
    
    date = datetime.datetime.now().strftime("%Y%m%d")

    reservation_query = (Reservations.select()
                               .where(Reservations.id_user == user)
                               .where(Reservations.timestamp == date)
                               .where((Reservations.status == 'TAKEN') | (Reservations.status == 'RESERVED')))
    if reservation_query.exists():
        reservation = reservation_query.get()
        building_name = reservation.id_desk.id_building.id_building.name
        number = reservation.id_desk.number.number
        return get_floor_data(building_name, number, date, token)
    else:
        return {'status': 'No current desk reserved or took', 'code': 'E011'}
    

