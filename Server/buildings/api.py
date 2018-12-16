from flask_restful import Resource
from webargs import fields
from webargs.flaskparser import use_kwargs, use_args

from buildings.helpers import get_building_data, create_building, get_buildings


class Building(Resource):
    building_args = {
        'building_name': fields.Str(required=False)
    }

    def get(self, building_name=None):
        if building_name:
            return get_building_data(building_name)
        else:
            return get_buildings()
    
    @use_args(building_args)
    def post(self, args):
        return create_building(args['building_name'])

    
