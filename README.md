# simple-ski-resort-project

Project with four Entities(User, Coach, Guest, SkiPass). You can CRUD all this entities with specific roles(ADMIN, MANAGER) and send them to with ActiveMQ.
Admin can do all CRUD opertations, meanwhile Manager just can show User,Coach, SkiPass, Guest and can do all operations with Guest.
Admin can also set photo to Coach(choice was between store photo as bytes in db and store url or path to storage) 
Also there is Scheduler using Actor(Akka Actors) which checks everyday at 12 p.m. skiPass duration 
and if there is less than one day left, inform the guest about it via ActiveMQ
