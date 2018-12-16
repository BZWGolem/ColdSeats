from common.models import Users, Buildings
from passlib.hash import bcrypt
import uuid


def create_user(username, password, building_name, token=None):
    user_query = Users.select().where(Users.username == username)
    building = Buildings.select().where(Buildings.name == building_name).get()
    
    if user_query.exists():
        return {'status': 'Username: "{}" exists'.format(username)}
    else:
        pass_hash = bcrypt.hash(password)
        Users.insert(username=username, pass_hash=pass_hash, token=token, id_building=building).execute()
        return {'Username: "{}" created'.format(username)}


def validate_user(username, password):
    try:
        user = Users.select().where(Users.username == username).get()
    except Users.DoesNotExist:
        return {'verify': False}

    if bcrypt.verify(password, user.pass_hash):
        token = uuid.uuid4()
        Users.update(token=token).where(Users.id_user == user).execute()
        return {'status': True, 'token': str(token)}
    return {'verify': False}
