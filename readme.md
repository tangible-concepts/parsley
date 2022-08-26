# Parsley - a free parser for Wardley Maps

Parsley takes one or more Wardley maps described in the grammar of [Online Wardley Maps] (https://www.onlinewardleymaps.com/),
converts the map into a graph and stores it in the Neo4J graph database. The main purpose for this is to apply structured queries
on the map, e.g. to find components that occur in several maps.

## How to use it
The easiest way to use Parsley is to run the [Docker](https://hub.docker.com/r/tangibleconcepts/parsley) image. 
You will need to have Docker pre-installed and running on your machine.

Once you have Docker installed and ready to go:

1. create an empty directory called the "working directory".
2. copy the file '[distribution/compose.yaml](https://github.com/tangible-concepts/parsley/blob/distribution/compose.yaml)' into the working directory. You do not need to check out the entire repo, but only copy or download the file into the working directory.
3. Create a directory with the name 'maps' within the working directory.
4. create a text file for each map to be imported into the 'maps' directory. Copy the textual description of the map into this file.
5. execute the following command in the working directory: 'docker compose up'.

Parsley starts an instance of the graph database and imports all maps from the maps directory into this instance.

Once the import is complete, open your browser on "localhost:7474" to open the Neo4j Query Console and explore your map.∆