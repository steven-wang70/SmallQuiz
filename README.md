# What's New
1) Support matrix questions. Please see details in API documentatio **Create a Matrix Question** and **Create an Option for Matrix Questions** for how to create questions and question options for matrix questions;
2) Pull questions to display for a user, and send users answer to question to the server. Please see details in the **For Readers** part of the API documentation.
# Getting Started

This project contains:
1) A Spring Boot application that provide RESTful services for partners to manipulate their online embedded questions:
    1) Create/Edit/Delete site informations;
    2) Create/Edit/Delete question definitions;
    3) Create/Edit/Delete question option definitions;
2) The same Spring Boot application that provide RESTful services for reader/browser from online content to:
    1) Pull full question definitions to be displayed in the online content;
    2) Receive readers' response to an embedded question.
3) A tes command line batch command to simulate the clinent run basic smoke test to the service;
4) A JUnit implementaion framework with two testcases to show case how to implement JUnit test cases for the service to do end-to-end test.

# How to Run the Service and Tests

Here we will only describe how to run from Eclipse environment. It does not cover how to run from command line.

## Load project into Eclipse
1) Open Eclipse;
2) Select menu item File/Import.... It will open a dialog;
3) In the dialog, select Maven/Existing Maven Projects. It will open another dialog;
4) Click "Browse..." to open this folder.

## Start and Smoke Test the RESTful Service
1) In the Eclipse "Package Explorer", locate the java file DemoApplication.java in the folder src/main/java;
2) Right click it, and select context menu "Run As/Java Application", you will see the Spring Boot starting process in the console window.
3) Open a command window, go to the project folder, run the smoke test batch file testService.bat. You can see something output like the content in the sample file testService.sample.output.txt. DO NOT stop the Spring Boot application at this point.
4) However, in the testService.batch, there is a command template is commented out. Because it could not be added to this batch file easily. This command is testing pulling questions from the service for display. To run this test, you need to copy this template out, replaced its tag <SITE_UUID> with the real UUID generated in the last test. Then run this command multiple time in the command line. You will find every time, it will pull the next question.

## Run JUnit Tests
1) In the Eclipse "Package Explorer", locate the java file RESTfulTests.java in the folder src/test/java. It is a test sute class.
2) Right click it, and select context menu "Run As/JUnit Test", you will see JUnit test started. For each test case, it will start/stop the Spring Boot application service, and run tests by sending HTTP requests to the service.

# Assumptions
For simplicity, this project will continue to use the H2 in memory database, which means when the service is stopped, the data is gone. So every time the service is started, it is guaranteed service data is clearly in consistent state.

# Security Considerations
The biggest concern about the current implementation is that the srevice can be abused from partner and reader side.
What if a partner tries to access/manipulate to questons of a website that they do not have ownership? What if a reader tries to pull questions and send answers from outside a webpage of the specified website? The current implementation has no solution to such attacks yet.
But we have potential solutions for such attacks.

## Security for Partners
For partners, we could use client side authentication to identify a partner. As long as we could identify a partner, we know which website that they have access. We could use this information to validate whether they have access to site informations, question informations, and question option informations, because all these informations have association with a website, directly or indirectly.

## Security for Readers
For readers, we could verify whether the request is valid by checking from which website the request comes from.
For invalid accesses, we could keep the logging of such activities, but just returns to client side HTTP satus NOT_FOUND(404), and nothing else.

# Scaling to Next Level
There are two types activities to access this service. One is partner activities to define questions, another one is reader activities to pull question and send back answers. Since questions are defined once and can be used many times, so the access volumne from partners is smaller comparing to reader activities.
From readers's perspective, there are two activities. One is pulling questions, another one is sending back answers. Since readers only reply to a portion of displayed questions, so the biggest acsess volume happens with the operation of pulling questions from the service. To perform better with such high volume access, we could manage a in memory cache for questions pulled by readers.
For even higher volume access, we may consider splitting the service into two microservices. One for partners to manage their questions, another one for readers to pull questions and send back answers.

# API Documentation

## For Partners
### Manage Site Infomation
#### Create Site
Request:
>curl --header "Content-Type: application/json" \
  	 --request POST \
  	 --data '{"url": "www.bob.com"}' \
  	 http://localhost:8080/sites
  	 
Response:
>{
  "siteId": 1,
  "siteUUID": "c8c27ebf-f25c-4856-83b5-bd6bdf828901",
  "url": "www.bob.com",
  "createdAt": "2020-03-21T23:32:52.239+0000",
  "updatedAt": "2020-03-21T23:32:52.239+0000"
}

#### Edit Site
Request:
>curl --header "Content-Type: application/json" \
  	 --request PUT \
  	 --data '{"url": "www.bob2.com"}' \
  	 http://localhost:8080/sites/1
  	 
Response:
>{
  "siteId": 1,
  "siteUUID": "680d1f22-dcf5-46f1-b5b4-1d36320ce78d",
  "url": "www.bob2.com",
  "createdAt": "2020-03-22T19:08:55.497+0000",
  "updatedAt": "2020-03-22T19:09:17.218+0000"
}

#### Delete Site
Request:
>curl --header "Content-Type: application/json" \
  	 --request DELETE \
  	 http://localhost:8080/sites/1
  	 
Response:
>{
  "siteId": 1,
  "siteUUID": "680d1f22-dcf5-46f1-b5b4-1d36320ce78d",
  "url": "www.bob2.com",
  "createdAt": "2020-03-22T19:08:55.497+0000",
  "updatedAt": "2020-03-22T19:09:17.218+0000"
}

#### List Sites
Request:
>curl --header "Content-Type: application/json" \
  	 --request GET \
  	 http://localhost:8080/sites
  	 
Response:
>[
  {
    "siteId": 1,
    "siteUUID": "00014349-e238-47be-afa3-22474a95267e",
    "url": "www.bob.com",
    "createdAt": "2020-03-22T19:13:57.360+0000",
    "updatedAt": "2020-03-22T19:13:57.360+0000"
  },
  {
    "siteId": 2,
    "siteUUID": "bad57280-2e75-4263-a652-1f5d41afdcc9",
    "url": "www.google.com",
    "createdAt": "2020-03-22T19:14:05.253+0000",
    "updatedAt": "2020-03-22T19:14:05.253+0000"
  }
]

#### Get a Site
Request:
>curl --header "Content-Type: application/json" \
  	 --request GET \
  	 http://localhost:8080/sites/1
  	 
Response:
>{
  "siteId": 1,
  "siteUUID": "00014349-e238-47be-afa3-22474a95267e",
  "url": "www.bob.com",
  "createdAt": "2020-03-22T19:13:57.360+0000",
  "updatedAt": "2020-03-22T19:13:57.360+0000"
}

### Manage Questions
#### Question Type Definitions
>TRIVIA(1)
POOL(2)
CHECKBOX(3)
MATRIX(4)

#### Create Question
##### Create a Normal Question
Request:
>curl --header "Content-Type: application/json" \
     --request POST 
     --data "{"siteId":1, "questionType": 1, "question": "how many toes does a pig have?"}" \ http://localhost:8080/sites/1/questions

Response:
>{
  "questionId": 2,
  "dimInfo": [],
  "question": "how many toes does a pig have?",
  "siteId": 1,
  "questionType": 1,
  "createdAt": "2020-03-21T23:32:52.435+0000",
  "updatedAt": "2020-03-21T23:32:52.449+0000"
}

##### Create a Matrix Question
Request:
>curl --header "Content-Type: application/json" \
     --request POST \
     --data "{"siteId":1, "questionType": 4, "dimInfo": ["Age", "Gender"], "question": "Please tell us a bit about yourself?"}" \
     http://localhost:8080/sites/1/questions

**NOTE:** For matrix questions, there is an additional property of array, which contains two element. The 1st element is the title for top to bottom options, the 2nd element is the title for left to right options.

Response:
>{
  "questionId": 6,
  "dimInfo": [ "Age", "Gender" ],
  "question": "Please tell us a bit about yourself?",
  "siteId": 1,
  "questionType": 4,
  "createdAt": "2020-03-21T23:32:52.804+0000",
  "updatedAt": "2020-03-21T23:32:52.804+0000"
}

#### Edit Question
Request:
>curl --header "Content-Type: application/json" \
  	 --request PUT \
  	 --data '{"siteId":1, "question": "how many toes does a horse have?"}' \
  	 http://localhost:8080/questions/2

Response:
>{
  "questionId": 2,
  "dimInfo": [],
  "question": "how many toes does a horse have?",
  "siteId": 1,
  "questionType": 1,
  "createdAt": "2020-03-21T23:32:52.435+0000",
  "updatedAt": "2020-03-21T23:32:52.449+0000"
}

#### Delete Question
Request:
>curl --header "Content-Type: application/json" \
  	 --request DELETE \
  	 http://localhost:8080/questions/2

Response:
>{
  "questionId": 2,
  "dimInfo": [],
  "question": "how many toes does a horse have?",
  "siteId": 1,
  "questionType": 1,
  "createdAt": "2020-03-21T23:32:52.435+0000",
  "updatedAt": "2020-03-21T23:32:52.449+0000"
}

#### List Questions for a Site
Request:
>curl --header "Content-Type: application/json" \
  	 --request GET \
  	 http://localhost:8080/sites/1/questions

Response:
>[
  {
    "questionId": 2,
    "dimInfo": null,
    "question": "how many toes does a pig have?",
    "siteId": 1,
    "questionType": 1,
    "createdAt": "2020-03-22T19:47:40.779+0000",
    "updatedAt": "2020-03-22T19:47:40.793+0000"
  },
  {
    "questionId": 6,
    "dimInfo": [
      "Age",
      "Gender"
    ],
    "question": "Please tell us a bit about yourself?",
    "siteId": 1,
    "questionType": 4,
    "createdAt": "2020-03-22T19:47:41.170+0000",
    "updatedAt": "2020-03-22T19:47:41.170+0000"
  }
]
#### Get a Question
Request:
>curl --header "Content-Type: application/json" \
  	 --request GET \
  	 http://localhost:8080/questions/2

Response:
>{
  "questionId": 2,
  "dimInfo": [],
  "question": "how many toes does a horse have?",
  "siteId": 1,
  "questionType": 1,
  "createdAt": "2020-03-21T23:32:52.435+0000",
  "updatedAt": "2020-03-21T23:32:52.449+0000"
}

### Manage Question Options
#### Create Question Option
Request:
>curl --header "Content-Type: application/json" \
    --request POST \
    --data "{"option": "4 toes", "optionIndex": 2, "isCorrectAnswer": true}" \
    http://localhost:8080/questions/2/options
  	 
Response:
>{
  "id": 3,
  "option": "4 toes",
  "optionIndex": 2,
  "optionDim": null,
  "questionId": 2,
  "createdAt": "2020-03-21T23:32:52.593+0000",
  "updatedAt": "2020-03-21T23:32:52.593+0000",
  "isCorrectOption": false
}

**NOTE:** The display of question options is ordered by user defined **optionIndex**, not the server-generated **id**.
#### Create an Option for Matrix Questions
Request:
>curl --header "Content-Type: application/json" \
    --request POST --data "{"option": "Male", "optionIndex": 1,\"optionDim": 1}" \
    http://localhost:8080/questions/6/options

 
 Response:
 >{
  "id": 10,
  "option": "Male",
  "optionIndex": 1,
  "optionDim": 1,
  "isCorrectOption": false,
  "questionId": 6,
  "createdAt": "2020-03-22T21:58:54.091+0000",
  "updatedAt": "2020-03-22T21:58:54.091+0000"
}

**NOTE:** For matrix question options, an additional property of **optionDim** is required to specify whether this option to be displayed top to bottom or left to right. If the value is 1, then it will be displayed left to right. Otherwise if it is 0 or null, it is displayed top to bottom.
#### Edit Question Option
Request:
>curl --header "Content-Type: application/json" \
  	 --request PUT \
  	 --data '{"answer": "3 toes","isisCorrectOption": false}' \
  	 http://localhost:8080/options/3
  	 
Response:
>{
  "id": 3,
  "option": "3 toes",
  "optionIndex": 2,
  "optionDim": null,
  "questionId": 2,
  "createdAt": "2020-03-21T23:32:52.593+0000",
  "updatedAt": "2020-03-21T23:32:52.593+0000",
  "isCorrectOption": false
}

#### Delete Question Option
Request:
>curl --header "Content-Type: application/json" \
  	 --request DELETE \
  	 http://localhost:8080/options/3
  	 
Response:
>{
  "id": 3,
  "option": "3 toes",
  "optionIndex": 2,
  "optionDim": null,
  "questionId": 2,
  "createdAt": "2020-03-21T23:32:52.593+0000",
  "updatedAt": "2020-03-21T23:32:52.593+0000",
  "isCorrectOption": false
}

#### List Question Options
Request:
>curl --header "Content-Type: application/json" \
     --request GET \
     http://localhost:8080/questions/6/options
 
 Response:
 >[
  {
    "id": 4,
    "option": "3 toes",
    "optionIndex": 1,
    "optionDim": null,
    "questionId": 2,
    "createdAt": "2020-03-22T19:47:41.022+0000",
    "updatedAt": "2020-03-22T19:47:41.022+0000",
    "isCorrectOption": false
  },
  {
    "id": 3,
    "option": "4 toes",
    "optionIndex": 2,
    "optionDim": null,
    "questionId": 2,
    "createdAt": "2020-03-22T19:47:40.938+0000",
    "updatedAt": "2020-03-22T19:47:40.938+0000",
    "isCorrectOption": false
  },
  {
    "id": 5,
    "option": "The do not have toes silly",
    "optionIndex": 3,
    "optionDim": null,
    "questionId": 2,
    "createdAt": "2020-03-22T19:47:41.103+0000",
    "updatedAt": "2020-03-22T19:47:41.103+0000",
    "isCorrectOption": false
  }
]

#### Get a Question Option
Request:
>curl --header "Content-Type: application/json" \
  	 --request GET \
  	 http://localhost:8080/options/3
  	 
Response:
>{
  "id": 3,
  "option": "3 toes",
  "optionIndex": 2,
  "optionDim": null,
  "questionId": 2,
  "createdAt": "2020-03-21T23:32:52.593+0000",
  "updatedAt": "2020-03-21T23:32:52.593+0000",
  "isCorrectOption": false
}

## For Readers
### Pull Questions
Request:
>curl --header "Content-Type: application/json" \
     --request GET \
     http://localhost:8080/nextquestion/**<SITE_UUID>**?reader=91693b12-284d-46d1-b6cb-6bc6be9acdd4

**NOTE:** This request will pull the next queston to be displayed. The response contains all information for display, but do not contain management related informations such as created/updated timestamp.

Response:
>{
  "questionId": 6,
  "dimInfo": [
    "Age",
    "Gender"
  ],
  "question": "Please tell us a bit about yourself?",
  "siteId": 1,
  "questionType": 4,
  "options": [
    {
      "id": 7,
      "option": "< 18",
      "optionIndex": 1,
      "optionDim": 0,
      "questionId": 6,
      "isCorrectOption": false
    },
    {
      "id": 8,
      "option": "18 to 55",
      "optionIndex": 2,
      "optionDim": 0,
      "questionId": 6,
      "isCorrectOption": false
    },
    {
      "id": 9,
      "option": "> 55",
      "optionIndex": 3,
      "optionDim": 0,
      "questionId": 6,
      "isCorrectOption": false
    },
    {
      "id": 10,
      "option": "Male",
      "optionIndex": 1,
      "optionDim": 1,
      "questionId": 6,
      "isCorrectOption": false
    },
    {
      "id": 11,
      "option": "Female",
      "optionIndex": 2,
      "optionDim": 1,
      "questionId": 6,
      "isCorrectOption": false
    }
  ]
}
### Send Back Question Answers
Request:
>curl --header "Content-Type: application/json" \
     --request POST \
     --data "{"readerUUID": "2da235c3-1543-4f78-acaa-53072c8a1d08", "questionId": 2, "answers": [3, 4]}" \
     http://localhost:8080/answers

**NOTE:** The property answers is an array of question option IDs. For matrix questions, the 1st element is mapping to options in the dimension 0 which displays top to bottom, the 2nd element is mapping to options in the dimension 1 which displays left to right.

Response:
>{
  "id": 12,
  "readerUUID": "2da235c3-1543-4f78-acaa-53072c8a1d08",
  "questionId": 2,
  "answers": [ 3, 4 ]
}
