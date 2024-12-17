![image](https://github.com/andreeaManuela/Integrated-Energy-Management-System/assets/126789711/354cc490-9d24-4d26-99dc-0b0ca7ee83d2)

Functional requirements:

➢ Users log in. Users are redirected to the page corresponding to their role.

➢ Administrator/Manager Role:

o CRUD operations on users

o CRUD operations on devices

o Create mapping user-device.

➢ User/Client Role

o A client can view on his/her page all the devices.

➢ The users corresponding to one role will not be able to access the pages corresponding to
other roles 

➢ The message-oriented middleware allows the Smart Metering Device Simulator to send
data tuples in a JSON format.

➢ The message consumer component of the microservice processes each message and
notifies asynchronously the client application using WebSocket.

➢ The hourly energy values will be saved by the consumer component in the Monitoring
database.

Chat microservice:

➢ The front-end application displays a chat box where users can type messages.

➢ The message is sent asynchronously to the administrator, that receives the message together
with the user identifier, being able to start a chat with the user.

➢ Messages can be sent back and forth between the user and the administrator during a chat
session.

➢ The administrator can chat with multiple users at once.

➢ A notification is displayed for the user when the other administrator reads the message and
vice versa.

Authorization component:
➢ One of the services is chosen as authorization server (e.g. User Microservice, or a newly
implemented microservice for authorization and authentication). This service generates
access tokens to the client application. The tokens will be used to access other
microservices. 

Implementation technologies
➢ REST for microservices (Java Spring REST) and JavaScript-based frameworks for client applications (ReactJS).
➢ RabbitMQ, WebSockets.
➢ Chat component: web sockets technology
➢ Authorization component: JWT based authorization - for user’s authentication and
authorization to all microservices. A authorization service that generates tokens that will be recognized by other
microservices which share the same secret key as the authorization service
