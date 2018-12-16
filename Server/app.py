from flask import Flask
from flask_restful import Api

from users.api import User
from buildings.api import Building
from floors.api import Floor
from desks.api import Desk

app = Flask(__name__)
api = Api(app)

api.add_resource(User, '/user')
api.add_resource(Building, '/building', '/building/<string:building_name>')
api.add_resource(Floor, '/floor')
api.add_resource(Desk, '/desk')


if __name__ == '__main__':
    # app.run(debug=True)
    app.run(host='0.0.0.0', port=5000)