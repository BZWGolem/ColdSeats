from flask_restful import Resource
from webargs import fields
from webargs.flaskparser import use_args

from users.helpers import create_user, validate_user


class User(Resource):
    post_args = {
        'username': fields.Str(required=True),
        'password': fields.Str(required=True)
    }

    put_args = {
        'username': fields.Str(required=True),
        'password': fields.Str(required=True),
        'token': fields.Str(required=False)
    }

    @use_args(post_args)
    def post(self, args):
        return validate_user(args['username'], args['password'])

    @use_args(put_args)
    def put(self, args):
        return create_user(args['username'], args['password'], args.get('token', None))
