# aa_fetch_assignment

Steps to start this project

1. Docker
 - The Dockerfile is inside the zip file
 - Please unzip the file and run the below commands after cloning the repository in your local
 - build the image `docker build -t fetch-processor .`
 - run the image `docker run -p 8080:8080 fetch-processor `

API
- All APIs defined in 
  - [ReceiptController.java](src/main/java/org/receipt/aa_fetch_assignment/controller/ReceiptController.java)
- POST request
  - `http://localhost:8080/receipts/process`
  - Request Body
    - `{
      "retailer": "Target",
      "purchaseDate": "2022-01-01",
      "purchaseTime": "13:01",
      "items": [
    {
          "shortDescription": "Mountain Dew 12PK",
          "price": "6.49"
    },{
          "shortDescription": "Emils Cheese Pizza",
          "price": "12.25"
    },{
          "shortDescription": "Knorr Creamy Chicken",
          "price": "1.26"
    },{
          "shortDescription": "Doritos Nacho Cheese",
          "price": "3.35"
    },{
          "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
          "price": "12.00"
    }
    ],
      "total": "35.35"
    }`
    - Response
      - `{
            "id": "31b8bb19-85da-4b64-8127-e563db159244"
        }`
- GET Request
  - `http://localhost:8080/receipts/<id>/points`
  - Request 
    - `http://localhost:8080/receipts/31b8bb19-85da-4b64-8127-e563db159244/points`
  - Response
    - `{
        "points": 28.0
        }`
