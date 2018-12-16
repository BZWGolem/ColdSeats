import requests

# python -m pwiz -e postgresql -u piotrbov wpam

# host = 'http://192.168.1.7:5000'
host = 'http://127.0.0.1:5000'
# host = 'http://68.183.243.230:5000'

floor_shape = '0,0,0,0,0,0,0,0,0,0;'*9 + '0,0,0,0,0,0,0,0,0,0'

floor_shape10 = '0,0,0,0,0,0,0,0,0,0;'*7 + '0,0,0,0,0,0,0,0,0,0'
floor_shape11 = '0,0,0,0,0,0,0,0,0,0,0;'*8 + '0,0,0,0,0,0,0,0,0,0,0'
floor_shape12 = '0,0,0,0,0,0,0,0,0,0,0,0,0;'*9 + '0,0,0,0,0,0,0,0,0,0,0,0,0'

floor_shape1 = '0,0,0,0,0,0;'*4 + '0,0,0,0,0,0'
floor_shape2 = '0,0,0;'*7 + '0,0,0'

# create buildings
create_building = requests.post(host+'/building', data={'building_name': 'Spire'})
create_building = requests.post(host+'/building', data={'building_name': 'Zebra'})

# create user
create = requests.put(host+'/user', data={'username': 'piotrbov@gmail.com', 'password': 'password',
                                          'token': 'f3b7f15d-9d92-4b8c-a78d-f2b93dab90e8',
                                          'building_name': 'Spire'})
create = requests.put(host+'/user', data={'username': 'kapoplawska@gmail.com', 'password': 'password', 'building_name': 'Spire'})
create = requests.put(host+'/user', data={'username': 'user1@gmail.com', 'password': 'password', 'building_name': 'Spire'})
create = requests.put(host+'/user', data={'username': 'user2@gmail.com', 'password': 'password', 'building_name': 'Spire'})
create = requests.put(host+'/user', data={'username': 'user3@gmail.com', 'password': 'password', 'building_name': 'Spire'})
create = requests.put(host+'/user', data={'username': 'user4@gmail.com', 'password': 'password', 'building_name': 'Spire'})
create = requests.put(host+'/user', data={'username': 'user5@gmail.com', 'password': 'password', 'building_name': 'Zebra'})
create = requests.put(host+'/user', data={'username': 'user6@gmail.com', 'password': 'password', 'building_name': 'Zebra'})
create = requests.put(host+'/user', data={'username': 'user7@gmail.com', 'password': 'password', 'building_name': 'Zebra'})
create = requests.put(host+'/user', data={'username': 'user8@gmail.com', 'password': 'password', 'building_name': 'Zebra'})

# create floors
get_building = requests.get(host+'/building', data={'id_building': '1'})
building = get_building.json()['building_list']

id_building = building[0]['id_building']

create_floor = requests.post(host+'/floor', data={'id_building': id_building,
                                                  'number': '10th',
                                                  'shape': floor_shape10})
create_floor = requests.post(host+'/floor', data={'id_building': id_building,
                                                  'number': '11th',
                                                  'shape': floor_shape})
create_floor = requests.post(host+'/floor', data={'id_building': id_building,
                                                  'number': '12th',
                                                  'shape': floor_shape})

# create desks
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th', 'nfc_id': "04AFAE72E74C80"})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th', 'nfc_id': "04B6B672E74C80"})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th', 'nfc_id': "0413AF72E74C80"})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th', 'nfc_id': "046DAE724C80"})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th', 'nfc_id': "04D4AD72E74C80"})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '10th'})


create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '11th'})


create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '12th'})

# add desks to floor
get_floor = requests.get(host+'/floor', data={'building_name': 'Spire',
                                              'number': '10th',
                                              'token': 'f3b7f15d-9d92-4b8c-a78d-f2b93dab90e8'})
floor_dict = get_floor.json()

pos_list = [[0, 5], [0, 6], [0, 7], [2, 5], [2, 6], [2, 7], [3, 5], [3, 6], [3, 7],
            [4, 0], [4, 1], [5, 5], [5, 6], [5, 7], [6, 0], [6, 1],
            [6, 5], [6, 6], [6, 7], [7, 0], [7, 1], [9, 0], [9, 1], [9, 7]]

for index, pos in enumerate(pos_list):
    object_data = {'id_building': id_building,
                   'number': '10th',
                   'id_object': floor_dict['desks_id'][index]['id_desk'],
                   'pos_x': pos[0],
                   'pos_y': pos[1]}
    print(object_data)
    add_object = requests.put(host+'/floor', data=object_data)

pos_list = [[3, 1], [5, 2], [10, 7], [8, 7], [8, 7], [5, 8], [1, 9], [0, 0], [3, 7], [10, 8]]
get_floor = requests.get(host+'/floor', data={'building_name': 'Spire',
                                              'number': '11th',
                                              'token': 'f3b7f15d-9d92-4b8c-a78d-f2b93dab90e8'})
floor_dict = get_floor.json()


for index, pos in enumerate(pos_list):
    object_data = {'id_building': id_building,
                   'number': '11th',
                   'id_object': floor_dict['desks_id'][index]['id_desk'],
                   'pos_x': pos[0],
                   'pos_y': pos[1]}
    print(object_data)
    add_object = requests.put(host+'/floor', data=object_data)

pos_list = [[2, 1], [5, 6], [12, 3], [1, 9], [8, 1], [3, 6], [11, 9], [7, 8], [1, 7], [3, 4]]
get_floor = requests.get(host+'/floor', data={'building_name': 'Spire',
                                              'number': '12th',
                                              'token': 'f3b7f15d-9d92-4b8c-a78d-f2b93dab90e8'})
floor_dict = get_floor.json()

for index, pos in enumerate(pos_list):
    object_data = {'id_building': id_building,
                   'number': '12th',
                   'id_object': floor_dict['desks_id'][index]['id_desk'],
                   'pos_x': pos[0],
                   'pos_y': pos[1]}
    print(object_data)
    add_object = requests.put(host+'/floor', data=object_data)


get_building = requests.get(host+'/building', data={'id_building': '1'})
building = get_building.json()['building_list']

id_building = building[1]['id_building']

create_floor = requests.post(host+'/floor', data={'id_building': id_building,
                                                  'number': '1st',
                                                  'shape': floor_shape1})
create_floor = requests.post(host+'/floor', data={'id_building': id_building,
                                                  'number': '2nd',
                                                  'shape': floor_shape2})

# create desks
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '1st'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '1st'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '1st'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '1st'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '1st'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '1st'})

create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '2nd'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '2nd'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '2nd'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '2nd'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '2nd'})
create_desk = requests.post(host+'/desk', data={'id_building': id_building, 'number': '2nd'})

# add desks to floor
get_floor = requests.get(host+'/floor', data={'building_name': 'Zebra',
                                              'number': '1st',
                                              'token': 'f3b7f15d-9d92-4b8c-a78d-f2b93dab90e8'})
floor_dict = get_floor.json()

pos_list = [[5, 0], [5, 2], [2, 1], [0, 4], [3, 3], [4, 3]]

for index, pos in enumerate(pos_list):
    object_data = {'id_building': id_building,
                   'number': '1st',
                   'id_object': floor_dict['desks_id'][index]['id_desk'],
                   'pos_x': pos[0],
                   'pos_y': pos[1]}
    print(object_data)
    add_object = requests.put(host+'/floor', data=object_data)

pos_list = [[2, 7], [0, 0], [2, 4], [1, 5], [0, 7], [1, 4]]
get_floor = requests.get(host+'/floor', data={'building_name': 'Zebra',
                                              'number': '2nd',
                                              'token': 'f3b7f15d-9d92-4b8c-a78d-f2b93dab90e8'})
floor_dict = get_floor.json()


for index, pos in enumerate(pos_list):
    object_data = {'id_building': id_building,
                   'number': '2nd',
                   'id_object': floor_dict['desks_id'][index]['id_desk'],
                   'pos_x': pos[0],
                   'pos_y': pos[1]}
    print(object_data)
    add_object = requests.put(host+'/floor', data=object_data)
