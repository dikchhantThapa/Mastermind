# Mastermind Game Application

This is a game where the computer chooses a secret list of random numbers and the player(s) try/tries to guess the number combinations. At the end of each
attempt to guess the 'n' number combinations, the computer will provide feedback whether the
player had guess a number correctly, or/and a number and digit correctly. A player must guess
the right number combinations within 'm' attempts to win the game.

## Note: 
- n: number of integers to guess
- m: number of attempts available to the players

 The game is built on Spring Boot 3 Framework (java 17) using Maven as a build tool. In order to run this application, you will need to install java 17 and compatible version of maven. Once done, clone the repository to local using git and then create a jar of the application. To do this, please navigate to the project folder and run the command ```mvn clean install```. Then, navigate to the subfolder named target using command line and run the command: ```java -jar mastermind-0.0.1.jar```. The application should run. By default, the game runs in localhost on port 8080. This game implements REST API to interact with the game and lacks a UI at this moment therefore, you will need to install postman, soapUI or any other software that 
can be used for REST API testing.


## Guide to playing
- ### Create a new game: 
  Before you can start playing, you need to create a new game. This can be done by making a **POST** api call to the endpoint **/api/v1/mastermind/game**. This endpoint accepts a request body with the following properties.

  - ***players*** : List of name of players
  - ***difficulty*** : Integer representing the total number of numbers the players are required to guess. This is there to add difficulty to the game.
  - ***totalTries*** : The total tries allowed to the players to guess the numbers


**Example**:
```json
  {
    "players" : ["player1","player2"],
    "difficulty" : 4,
    "totalTries" : 8
  }
```
The end point returns a response with following properties if sucessful. 

  - ***msg*** : A string denoting that the creation of a new game was sucessful.
  - ***gameId*** : An unique alpha-numeric id generated for the game. This is used to identify a specific game among other games that are currently existing. 

**Example**:

```json
{
    "msg": "Game Sucessfully Created!",
    "gameId": "d52a96f7-3973-49f7-8ae1-336baf050bd5"
}
```
If an exception was encountered during game creation a response with exception will be sent. The exception usually occurs if there was an issue on the system side while fetching the random numbers from the random number API. The exception msg will have following properties.

  - ***msg*** : A string denoting the exception reason
  - ***code*** : HTTP status code for the exception type.

**Example**:

```json
{
    "msg": "Could not initialize game, try again later!",
    "code": 500
}
```

- ### Playing the game:
In order to play the game a ***POST*** API call needs to be made to the endpoint: ****/api/v1/mastermind/game/{id}/play****. Here the placeholder {id} represents the game id that was generated during the creation of the game. For instance, using the example of game creation from above, in order to play the game with id ****d52a96f7-3973-49f7-8ae1-336baf050bd5****, the endpoint would be ****/api/v1/mastermind/game/d52a96f7-3973-49f7-8ae1-336baf050bd5/play****. This endpoint accepts a request body with the following properties:


**Example**:

```json
{
  "userId" : "player1",    
  "guess" : [2,3,5,3]
}
```

The system accepts requests to this endpoint from only players that are authorized to play the particular game. The users allowed to play a game are configured during game intitialization itself as shown in the previous examples. In an event when an unauthorized player tries to play the game, an error response with following properties is sent back to the user.

  - ***msg*** : A string denoting the exception reason
  - ***code*** : HTTP status code for the exception type.

**Example**:

```json
{
    "code": 400,
    "msg": "Player is not part of this game!"
}
```
In the scenario of a sucessful guessing attempt by user a response with following properties is returned by the api:


##### `numOfTriesleft`

- *(Type: Integer)* The number of attempts the player has left to guess the correct sequence.

##### `hasWon`

- *(Type: Boolean)* Indicates whether the player has won the game (`true`) or not (`false`).

##### `moveHistory`

- *(Type: Array)* An array containing objects representing each move made by the player.

  - **`guessedNumbers`**
    - *(Type: Array)* The sequence of numbers guessed by the player in a particular move.

  - **`moveNumber`**
    - *(Type: Integer)* The number assigned to the move indicating the order in which it was made.

  - **`moveResult`**
    - *(Type: String)* A message describing the result of the move, indicating the number of correct numbers and correct locations.

##### Example

```json
{
    "numOfTriesleft": 7,
    "hasWon": false,
    "moveHistory": [
        {
            "guessedNumbers": [2, 3, 5, 3],
            "moveNumber": 1,
            "moveResult": "2 correct number and 2 correct location"
        }
    ]
}
 ```
If the player guesses the numbers correct, they win. In this case the hasWon property in the above json msg is set to true. Once the user runs out of tries the user sees the following error response: 

```json
{
    "code": 400,
    "msg": "Player has no more tries left"
}
```
If all the users in the game, run out of tries, the game ends as a draw and the following response is sent:

```json
{
    "code": 400,
    "msg": "This game is already over! Result: game was a draw!"
}
```

If the users try to access a game which has either ended as one of the player winning or a draw a response shown above is sent with the msg property denoting how the game had ended. In case of a decisive win, the following response will be sent:

For example Player1 Won the game:
```json
{
    "code": 400,
    "msg": "This game is already over! Result: player1 won"
}
```