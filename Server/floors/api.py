from flask_restful import Resource
from webargs import fields
from webargs.flaskparser import use_args

from floors.helpers import get_floor_data, create_floor, create_object


class Floor(Resource):
    get_args = {
        'building_name': fields.Str(required=True),
        'number': fields.Str(required=True),
        'date': fields.Str(missing=''),
        'token': fields.Str(required=True)
    }

    post_args = {
        'id_building': fields.Int(required=True),
        'number': fields.Str(required=True),
        'shape': fields.Str(required=True)
    }

    put_args = {
        'id_building': fields.Int(required=True),
        'number': fields.Str(required=True),
        'id_object': fields.Int(required=True),
        'pos_x': fields.Int(required=True),
        'pos_y': fields.Int(required=True)
    }

    @use_args(get_args)
    def get(self, args):
        return get_floor_data(args['building_name'], args['number'], args['date'], args['token'])
    
    @use_args(post_args)
    def post(self, args):
        return create_floor(args['id_building'], args['number'], args['shape'])
    
    @use_args(put_args)
    def put(self, args):
        return create_object(args['id_building'], args['number'], args['id_object'],
                             args['pos_x'], args['pos_y'])
    


    
