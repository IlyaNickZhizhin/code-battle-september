This project represents a basic java websocket client for the codenjoy platform.
It allows you to easily and quickly join the game, developing your unique algorithm, having a configured infrastructure.

## Custom Setup Instructions

After cloning the repository:

1. Configure your client by modifying the `config.yaml` file with the appropriate data.
2. Make mvn clean install via you IDE or in shell(bash) command line from root folder of project.
3. In the class `codenjoy-clients/src/main/java/com/codenjoy/dojo/JavaRunner.java`, set the value of the variable `private String url` to the value provided on the arena page (the address should look something like this - "https://dojorena.io/codenjoy-contest/board/player/dojorena5151?code=1234567891011121314"). Also, check the value of `private String game`, it should be "rawelbbub".
4. Run `codenjoy-clients/src/main/java/com/codenjoy/dojo/JavaRunner.java` in your favorite IDE. Ensure that the program works correctly, and after stopping it, the files `turns.ser` (repository where your game moves are saved) and `tokens.txt` (file where the total token usage is calculated) appear in `codenjoy-clients/logs`.

**WARNING!!! To avoid incorrect operation:**
1. **Always** DELETE the `turns.ser` history after each round ends, and send tokens.txt to organizing committee.
2. **Never** delete the move history during a round.

## Instructions for Writing Code to Create Prompts

You are provided with a minimal set of classes for working on creating prompts. In the folder `codenjoy-clients/src/main/resources/prompts` you can find the following files:
1. `game_SYSTEM_prompt` - detailed game rules.
2. `LONG_SYSTEM_PROMPT` and `SHORT_SYSTEM_PROMPT` - templates for creating system prompts for GPT chat models.
3. `LONG_USER_PROMPT` and `SHORT_USER_PROMPT` - templates for creating user prompts for GPT chat models.

As you can see from the names, prompts can be short or long. Once every few turns (the exact frequency is set in the `stepsForRefreshRules` line of the `config.yaml` file), a prompt based on the long template is sent to the AI model, while the rest of the time a prompt based on the short template is used.

In the templates, you can place placeholders that can subsequently be replaced according to the logic you define in the processor class (`src/main/java/com/codenjoy/dojo/games/rawelbbub/PrompterProcessor.java`).

You can do this according to the example presented in the `prepareLongUserPrompt` method:

```java
private String prepareLongUserPrompt(Board board, int coolDown) throws URISyntaxException, IOException {
    String template = Files.readString(Path.of(ClassLoader.getSystemResource("prompts/LONG_USER_PROMPT").toURI()));
    return new PromptBuilder(template)
            .append("PLACE_FOR_BOARD", "\n"+board.toString()) // using simple logic
            .append("PLACE_FOR_PREVIOUS_TURNS", new PreviousTurnsReplacer()) // creating your custom class implementing the RepositoryPlaceholderReplacer interface
            .append("TURNS_FOR_ACT", coolDown == 0 ? "AVAILABLE" : "DONT SHOOT, it will be available in " + coolDown + " turns") // manually inserting text
            .append("YOUR_CUSTOM_PLACEHOLDER_WITH_INFO_FROM_BOARD", 
                    (BoardPlaceholderReplacer) (brd) -> "IMPLEMENT YOUR LOGIC HERE") // using a lambda expression
            .append("YOUR_CUSTOM_PLACEHOLDER_WITH_INFO_FROM_REPOSITORY",
                    (RepositoryPlaceholderReplacer) () -> "IMPLEMENT YOUR LOGIC HERE")
            .build(board); // using a lambda expression
}
```
Experiment and get optimal responses from artificial intelligence.

# What do you need to get started?
To get started, you should define the desired game and enter a value in `com.codenjoy.dojo.JavaRunner.GAME`. The class can be found under src/main/java/com/codenjoy/dojo. \
GAME is a variable declared in JavaRunner Class.\
The second important thing is the connection token to the server. After successful authorization on the site, you must copy the url
and enter a value in `com.codenjoy.dojo.JavaRunner.URL`. \
URL is a variable declared in JavaRunner Class.
This is enough to connect and participate in the competition.

# How to run it?
To start a project from the console window, you must first perform build with Maven `mvn clean package assembly:single -Pjar-with-dependencies -DskipTests=true`.
The entry point for starting a project is `com.codenjoy.dojo.JavaRunner.main(args)`. \
You can pass the game type and token connection to the server as command-line arguments.
Game parameters passed by arguments at startup have a higher priority than those defined in the code.

The archive is run with the command `java -jar target/client-exec.jar [<game>] [<url>]`

# How does it work?
The elements on the map are defined in `com.codenjoy.dojo.games.<gamename>.Element`. They determine the meaning of a particular symbol.
The two important components of the game are the `com.codenjoy.dojo.games.<gamename>.Board` game board 
and the `com.codenjoy.dojo.games.<gamename>.YourSolver` solver.

Every second the server sends a string representation of the current state of the board, which is parsed in an object of class `Board`.
Then the server expects a string representation of your bot's action that is computed by executing `YourSolver.get(board)`.

Using the set of available methods of the `Board` class, you improve the algorithm of the bot's behavior.
You should develop this class, extending it with new methods that will be your tool in the fight.
For example, a bot can get information about an element in a specific coordinate by calling `AbstractBoard.getAt(x, y)`
or count the number of elements of a certain type near the coordinate by calling `AbstractBoard.countNear(x, y, element)`, etc.

# Business logic testing
Writing tests will allow you to create conclusive evidence of the correctness of the existing code.
This is your faithful friend, who is always ready to answer the question: "Is everything working as I expect? The new code did not break my existing logic?". \
The `com.codenjoy.dojo.client.<gamename>.BoardTest` class contains a set of tests that check board tools.
Implementation of new methods should be accompanied by writing new tests and checking the results of processing existing ones. \
Use `com.codenjoy.dojo.games.<gamename>.YourSolverTest.should_when()` to check the bot's behavior for a specific game scenario.