from common.models import Buildings, Floors


def get_building_data(building_name):
    try:
        building = (Buildings.select(Buildings)
                             .where(Buildings.name == building_name)
                             .get())
    except Buildings.DoesNotExist:
        return {'status': 'No building found', 'code': 'E005'}
    
    try:
        floor_query = Floors.select().where(Floors.id_building == building.id_building)
        
        floor_list = [{'floor_name': floor.number} for floor in floor_query]
    except Floors.DoesNotExist:
        floor_list = []
    
    building_dict = {'building_name': building.name,
                     'id_building': building.id_building,
                     'floor_list': floor_list}
    return building_dict


def create_building(name):
    building, created = Buildings.get_or_create(name=name)

    if not created:
        return {'status': 'Building exist', 'code': 'W001'}
    return {'status': 'Building created'}


def get_buildings():
    query = Buildings.select()

    return {'building_list': [{'building_name': building.name,
            'id_building': building.id_building} for building in query]}

