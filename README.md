# grpc-demo

A very naive demo to examine the performance charateristics of grpc.. 

grpc-server -- can be run by running com.darthshana.server.CharacterServer it provides two services
--updateCharacter to update a single character message and sends back a response
--updateCharacterStream which update charaters and sends back responces in a stream (bi-direction streaming)

grpc-client -- has two clients to call the grpc-server
--SimpleCharacterClient which calls updateCharacter 100 times syncronously and times how long it takes to complete
--StreamingCharacterClient which calls updateCharacterStream in a stream with a 100 missages, and times how long it takes to get the 100 responces back from the server.

rest-server -- a spring boot application which can be run by running com.darthshana.server.Application
has 
--com.darthshana.resource.CharacterResource which exoses a simple post to update a character..
--com.darthshana.client.PostClient which also makes 100 http 1.1 posts to the REST app and times it..

Just to get some meaning full results pushed both servers as a docker images and exposed it as a kuberneties service on google compute engine.. 
   
100 REST calls (syncronous) took -> 35369 ms
100 GRPC calls (syncronous) took -> 33742 ms

the 100 steaming requests completed almost 100 times faster..
