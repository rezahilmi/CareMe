# Backend - CareMe
Bangkit Capstone Project 2024

## Our Team
| Bangkit ID | Name |
| ---------- | ---- |
| C006D4KY0084 | Muhammad Yusuf Affandy |
| C006D4KY0278 | Muhammad Rafi Faisal |

## API Description
This is source code API of the Backend Care Me that is used by our Care Me mobile application. This API is built using Node.js, Express framework, Jsonwebtoken, Google cloud library (Firestore & Storage), and Tensorflow.js. We use Jsonwebtoken for authorization when using the API to identify the user. We use Firestore as a user database and Cloud Storage Bucket to save uploaded image from user.

### API URL
[Backend Care Me API](https://care-me-backend-k7yvugjo4q-et.a.run.app/)

### API Endpoints
| Endpoint | Method | Request Body | Content-Type | Authorization | Query Parameters |
| -------- | ------ | ------------ | ------------ | ---- | ---- |
| /register | POST | name, email, password | application/json | - | - |
| /login | POST | email, password | application/json | - | - |
| /predict | POST | image | multipart/form-data | Bearer <token> | - |
| /history | GET | - | - | Bearer <token> | page, size |
| /history/:historyId | GET | - | - | Bearer <token> | - |

## Running The Code

There are 2 ways to run the following code, which are with local terminal (Node.js) or with container (Docker). Here are the general step.
1.  Open new terminal
1.  Clone the repositories ```git clone https://github.com/rezahilmi/CareMe.git -b backend-cc care-me-backend```
1.  Enter the directory ```cd care-me-backend```
1.  Run ```cp .env.example .env``` to make new .env file
1.  Fill the .env file with proper value

### Running with Terminal Node.js

Make sure you have [Node.js](https://nodejs.org/en) installed on the system. This API uses Node.js version 20.13.1. Do the step [before](#running-the-code) Then follow this instruction:
1.  Use the terminal before or open new terminal in the prpject root directory (make sure you are in the ```care-me-backend/``` folder)
1.  Run ```npm install``` to install Node.js dependencies
1.  Run the server using the command ```npm start```
1.  The server will run in the ```localhost:<port>```
1.  Open Postman or web browser to access the API

To stop the server, simply press ```Ctrl+C``` on the keyboad while on the terminal.

### Running with Container
Make sure you have [Docker](https://www.docker.com/) installed on the system. Do the step [before](#running-the-code) Then follow this instruction:
1.  Use the terminal before or open new terminal in the prpject root directory (make sure you are in the ```care-me-backend/``` folder)
1.  Run the command ```docker build -t care-me-backend .``` to build the image
1.  After image has been build, run the container using the command ```docker run -p <port>:<port> care-me-backend```
1.  The server will run in the ```localhost:<port>```
1.  Open Postman or web browser to access the API

If there any permission error, try use ```sudo```. To stop the container do the following:
1.  Open new terminal
1.  Run the command ```docker ps```
1.  Copy the Running Container ID
1.  Run the command ```docker kill <containerID>```