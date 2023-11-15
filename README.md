# PokeSearch

This is my Pokemon Search app.
The app starts at the Search screen and lets a user choose from selected pokemon in the database to see their stats.
Selecting a pokemon fromt he drop down menu will take you to a page with the found pokemon. Clicking on it will take 
  you to a screen with all the information on the selected pokemon.
You can instead do an advanced search to find pokemon with more specific attributes. (Searching by stats will come very soon)
That will take you to a results screen with all the pokemon that match that query. 
Last thing you can do is click on Play Game.
This will take you to a screen where you get some details and information about the game.
When you find the pokemon, you will get a notification of success.
Clicking RANDOM will change the pokemon to search.
Clicking START will open a map where you can search for the pokemon and you are given a hint on where you an find it.

Notes:
I will be adding more stats to the advanced search screen.
I have a pokeball on the search page that needs to be animated for rubric requirements
The pokeball is supposed to spin while the database is being populated.
There is navigation in the nav_graph.xml
I have a database and a repository and using MVVM.
I have a WorkMananger thing going on as well.
I use notifications and geofencing.


I have added permissions on the MapFragment page. They update location even if user turns off location, then turns it back on.
I have added some Property Animation in the PokemonInfo screen  with the stats. THe dial is moved to the correlating number.
