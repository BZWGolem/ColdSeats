from flask_restful import Resource
from webargs import fields
from webargs.flaskparser import use_args

from desks.helpers import (create_desk, delete_reservation, desk_confirm,
                           desk_reserve, get_desk_data)


class Desk(Resource):
    get_args = {
        'id_desk': fields.Int(required=True),
        'date': fields.Str(missing=''),
        'token': fields.Str(required=False)
    }

    post_args = {
        'id_building': fields.Int(required=True),
        'number': fields.Str(required=True),
        'nfc_id': fields.Str(required=False)
    }

    put_args = {
        'nfc_id': fields.Str(missing=None),
        'id_desk': fields.Int(missing=None),
        'date': fields.Str(required=None),
        'token': fields.Str(required=True)
    }

    delete_args = {
        'token': fields.Str(required=True),
        'date': fields.Str(required=True),
    }

    @use_args(get_args)
    def get(self, args):
        return get_desk_data(args['id_desk'], args['date'], args.get('token', None))

    @use_args(post_args)
    def post(self, args):
        return create_desk(args['id_building'], args['number'], args.get('nfc_id', None))

    @use_args(put_args)
    def put(self, args):
        if args['nfc_id']:
            return desk_confirm(args['nfc_id'], args['token'])
        elif args['id_desk']:
            return desk_reserve(args['id_desk'], args['date'], args['token'])
    
    @use_args(delete_args)
    def delete(self, args):
        return delete_reservation(args['token'], args['date'])
