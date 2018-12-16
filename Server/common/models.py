from peewee import *

database = PostgresqlDatabase('wpam', **{'user': 'piotrbov'})


class UnknownField(object):
    def __init__(self, *_, **__): pass

class BaseModel(Model):
    class Meta:
        database = database

class Buildings(BaseModel):
    id_building = AutoField()
    name = CharField(null=True, unique=True)

    class Meta:
        table_name = 'buildings'

class Floors(BaseModel):
    id_building = ForeignKeyField(column_name='id_building', field='id_building', model=Buildings)
    number = TextField()
    shape = TextField(null=True)

    class Meta:
        table_name = 'floors'
        indexes = (
            (('id_building', 'number'), True),
        )
        primary_key = CompositeKey('id_building', 'number')

class Desks(BaseModel):
    id_building = ForeignKeyField(backref='floors_id_building_set', column_name='id_building', field='number', model=Floors)
    id_desk = AutoField()
    number = ForeignKeyField(backref='floors_number_set', column_name='number', field='number', model=Floors)
    nfc_id = TextField(null=True)

    class Meta:
        table_name = 'desks'

class Users(BaseModel):
    id_user = AutoField()
    pass_hash = TextField(null=True)
    token = TextField(null=True)
    username = CharField(null=True, unique=True)
    id_building = ForeignKeyField(backref='buildings_id_building_set', column_name='id_building', field='id_building', model=Buildings)

    class Meta:
        table_name = 'users'

class Reservations(BaseModel):
    id_desk = ForeignKeyField(column_name='id_desk', field='id_desk', model=Desks, null=True)
    id_user = ForeignKeyField(column_name='id_user', field='id_user', model=Users, null=True)
    timestamp = TextField(null=True)
    status = TextField(null=True)
    id_reservation = AutoField()

    class Meta:
        table_name = 'reservations'

