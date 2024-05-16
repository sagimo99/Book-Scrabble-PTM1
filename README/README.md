# FinalProjectPATAM1
Scrabble Book Game - Final Project (Advanced Software Development-1)

This is the final project for the Advanced Software Development-1 course. The project consists of three main parts, and this README currently covers the first part, which involves creating the "Scrabble Book" game with a server-side component.

Part 1: Scrabble Book Game Server

The first part of the project is the development of a server-side application for the "Scrabble Book" game. This part includes the following four main files:

1. Board.java: This file contains the implementation of the game board and its associated logic.
2. Tile.java: This file defines the structure and behavior of the individual tiles used in the game.
3. Word.java: This file handles the management and validation of words formed during the game.
4. MainTrain.java: This is the main entry point for the application, responsible for running and testing the game logic.
The server-side application is designed to handle the core functionality of the "Scrabble Book" game, including board management, tile placement, word validation, and scoring.



In this Part 2, we aim to implement the logic for searching words in a book dictionary efficiently. Our solution must be scalable, ensuring it can handle an increasing number of books and concurrent service requests without a significant rise in resource usage. The books are provided as text files, and for each query, we need to search for the presence of a word across all files. To achieve this, our system will utilize several filters:

Cache Manager: Maintains in-memory answers to common queries for quick access.
Bloom Filter: Efficiently determines word existence with high probability.
I/O Based Search: Conducted when a user challenges the dictionary's accuracy.
The Cache Manager will be implemented using a strategy pattern to allow flexible cache replacement policies such as LRU and LFU, ensuring efficient memory use while keeping the cache size within limits. This structured approach will help us maintain high performance and reliability as the system scales.


in this part 3, we will develop the MyServer class, which meets the specifications for handling client connections and processing requests using a ClientHandler interface. The MyServer class will be initialized with a port and a ClientHandler instance. The start method will run the server in the background, continuously listening for client connections and handling them one at a time. The close method will ensure an orderly shutdown by closing streams, sockets, and the server socket, with all closures verified in a finally block.

Additionally, we will implement the DictionaryManager class, designed to manage multiple dictionaries efficiently, allowing shared instances among clients. This class will handle creating new dictionaries as needed and support query and challenge methods. It will also update relevant caches for future queries, enhancing performance. The BookScrabbleHandler will utilize DictionaryManager to process client requests, returning appropriate responses based on the dictionary checks. This setup ensures that our server can manage word legality queries effectively, setting the stage for future enhancements like supporting multiple simultaneous clients.
