@ECHO off
REM Create a Site
curl --header "Content-Type: application/json" --request POST --data "{\"url\": \"www.bob.com\"}" http://localhost:8080/sites
echo.
echo.

REM Create the Trivia Question for the Site
curl --header "Content-Type: application/json" --request POST --data "{\"siteId\":1, \"questionType\": 1, \"question\": \"how many toes does a pig have?\"}" http://localhost:8080/questions
echo.
echo.

REM Create some responses for the question
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"4 toes\", \"optionIndex\": 2, \"isCorrectAnswer\": true}" http://localhost:8080/questions/2/options
echo.
echo.

curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"3 toes\", \"optionIndex\": 1}" http://localhost:8080/questions/2/options
echo.
echo.
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"The do not have toes silly\", \"optionIndex\": 3}" http://localhost:8080/questions/2/options
echo.
echo.


REM Create the matrix Question for the Site
curl --header "Content-Type: application/json" --request POST --data "{\"siteId\":1, \"questionType\": 4, \"dimInfo\": [\"Age\", \"Gender\"], \"question\": \"Please tell us a bit about yourself?\"}" http://localhost:8080/questions
echo.
echo.
REM dim top to bottom
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"^< 18\", \"optionIndex\": 1, \"optionDim\": 0}" http://localhost:8080/questions/6/options
echo.
echo.
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"18 to 55\", \"optionIndex\": 2, \"optionDim\": 0}" http://localhost:8080/questions/6/options
echo.
echo.
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"^> 55\", \"optionIndex\": 3, \"optionDim\": 0}" http://localhost:8080/questions/6/options
echo.
echo.
REM dim left to right
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"Male\", \"optionIndex\": 1, \"optionDim\": 1}" http://localhost:8080/questions/6/options
echo.
echo.
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"Female\", \"optionIndex\": 2, \"optionDim\": 1}" http://localhost:8080/questions/6/options
echo.
echo.

REM negative test. optionDim of value 2 is not valid
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"Female\", \"optionIndex\": 2, \"optionDim\": 2}" http://localhost:8080/questions/6/options
echo.
echo.

REM edit problem options
REM {"id":5,"questionId":2,"option":"The do not have toes silly","optionIndex":3,"optionDim":null,"isCorrectOption":false,"createdAt":"2020-03-21T00:51:14.760+0000","updatedAt":"2020-03-21T00:51:14.760+0000"}
curl --header "Content-Type: application/json" --request PUT --data "{\"optionId\": 5, \"option\": \"UPDATED: The do not have toes silly\", \"optionIndex\": 5}" http://localhost:8080/options/5
echo.
echo.

REM post an answer
curl --header "Content-Type: application/json" --request POST --data "{\"readerUUID\": \"2da235c3-1543-4f78-acaa-53072c8a1d08\", \"questionId\": 2, \"answers\": [3, 4]}" http://localhost:8080/answers
echo.
echo.

REM test pulling questions to display
REM curl --header "Content-Type: application/json" --request GET http://localhost:8080/nextquestion/<SITE_UUID>?reader=91693b12-284d-46d1-b6cb-6bc6be9acdd4

ECHO on

