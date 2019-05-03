# Java Mini Project 2019 - Logic Maze Runner

This code is simply an illustration of the projects given to us at our college. They help us illustrate our skills and interest in computer graphics, database integration and object oriented design, during our sophomore years.

We have made sure this project so simple that any sort of update, modification and/or reuse is greatly simplified; by use of this README, and the internal documentation within the code.

## Project Summary

This Project is a game, similar to the _T-Rex Runner_ game found in Chrome browsers, but with a logical twist. It's been christened the **Logical Maze Runner**, and this game is intended to provide the user with a **_once in a lifetime experience_**(we believe the player might never wish to play again)

This 2D retro game provides the player with a magic wand, and this wand is able to yield two weapons: _a fire weapon and a water weapon_. Similarly, the player is faced with two adversaries, _a fire monster and a water monster_. The player is able to kill the adversary by yielding the weapon of it's opposite type, i.e. **fire kills water, and water kills fire**.

The crux of this game is the _wave-like speed control_... This is implemented by the `runner.Game.updateGameSpeed()` functione, which constantly updates the speed of the game by modifying the frame-rate, in a sine-wave like fashion. The game gradually increases its speed(representing a _wave_) and then gradually decreases the speed(representing the _passing of a wave_).

The project also has the score module, that saves its data into an SQL database(we were restricted from using noSQL technologies because we had to comply to the project instructions).

## Project Configuration

This project contains a `Config.java` file, which contains a public class with all the constants you can edit! It's amazing the types of things that can be modified, and the flexibility that awaits to be discovered. Another few important things;
 + The project resources, like fonts(`.ttf`), audio(`.wav`), and images(`.png`) are in the `res/` folder of the project directory.
 + The SQL connector library, in a jar file, in the `lib/` folder of the project directory.
 + The actual code, in the `runner/` folder, under the `runner.*` package.

The `tileslow.png` file, is designed to contain all the player images(as a single image), and the image data is loaded in the form of _tiles_ into the program. This means that subimages of the main image are created. Not only does this save memory, but it also prevents the creation of too many streams, which turns out to be largely beneficial. Also, a lot of rectangular bound configuration variables need to be modified as well...

Based on the MySQL database configuration setup within your system, you will need to modify the database connection, the username and password in the list of DB variables in `Config.java`.
_(default configs are "jdbc:mysql://localhost:3306/runnerscoreDB", "root", ""), scores: (name, score)_.
This means you are expected to have an SQL server, with the username _root_, having an _empty password_ a database _runnerscoreDB_, and a table called _scores_ with two columns: _name_ & _score_

## Project Execution

This project can be easily executed by opening a _terminal/CMD_ at the _project root_ directory, and typing in the following commands,

``` bash
$ javac runner/*.java
$ jar cvfm Runner.jar MANIFEST.MF runner/* lib/* res/*
$ java -jar Runner.jar
```

This should run the project as an executable jar file.

## Conclusion

I am sharing my opinion here, but honestly, Java is a language entering that phase where only maintainers need to know about it(like PHP). I agree that learning Java does help educate budding engineers about the basic concepts of Object Oriented Programming, but other than that, I would recommend the newer generation of developers to take to newer and more abstract languages. DevSecOps is a huge ecosystem, and I do not wish to bias minds with a list of languages that must be learnt. In the end, _engineering is about solving problems your way_, and helping the masses, including the engineering community. How you implement a solution, which frameworks/platforms you choose, do not matter unless it may lead to problems in the future.

This project has been uploaded to Github, and is not intended to portray any interest towards game development, and it should just be viewed as a project written by an individual interested in learning Computer Science as a whole. Thank you for reading through this document!