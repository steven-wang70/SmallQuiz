REM Create a Site
curl --header "Content-Type: application/json" --request POST --data "{\"url\": \"www.bob.com\"}" http://localhost:8080/sites

REM Create the Trivia Question for the Site
curl --header "Content-Type: application/json" --request POST --data "{\"siteId\":1, \"questionType\": 1, \"question\": \"how many toes does a pig have?\"}" http://localhost:8080/questions

REM Create some responses for the question
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"4 toes\", \"optionIndex\": 2, \"isCorrectAnswer\": true}" http://localhost:8080/questions/2/options
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"3 toes\", \"optionIndex\": 1}" http://localhost:8080/questions/2/options
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"The do not have toes silly\", \"optionIndex\": 3}" http://localhost:8080/questions/2/options


REM Create the matrix Question for the Site
curl --header "Content-Type: application/json" --request POST --data "{\"siteId\":1, \"questionType\": 4, \"dimInfo\": [\"Age\", \"Gender\"], \"question\": \"Please tell us a bit about yourself?\"}" http://localhost:8080/questions
REM dim top to bottom
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"^< 18\", \"optionIndex\": 1, \"optionDim\": 0}" http://localhost:8080/questions/6/options
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"18 to 55\", \"optionIndex\": 2, \"optionDim\": 0}" http://localhost:8080/questions/6/options
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"^> 55\", \"optionIndex\": 3, \"optionDim\": 0}" http://localhost:8080/questions/6/options
REM dim left to right
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"Male\", \"optionIndex\": 1, \"optionDim\": 1}" http://localhost:8080/questions/6/options
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"Female\", \"optionIndex\": 2, \"optionDim\": 1}" http://localhost:8080/questions/6/options

REM negative test. optionDim of value 2 is not valid
curl --header "Content-Type: application/json" --request POST --data "{\"option\": \"Female\", \"optionIndex\": 2, \"optionDim\": 2}" http://localhost:8080/questions/6/options

REM edit problem options
REM {"id":5,"questionId":2,"option":"The do not have toes silly","optionIndex":3,"optionDim":null,"isCorrectOption":false,"createdAt":"2020-03-21T00:51:14.760+0000","updatedAt":"2020-03-21T00:51:14.760+0000"}
curl --header "Content-Type: application/json" --request PUT --data "{\"optionId\": 5, \"option\": \"UPDATED: The do not have toes silly\", \"optionIndex\": 5}" http://localhost:8080/options/5

