# Getting it to run
docker run --rm -it -p 7687:7687 -p 7473:7473 --name parsley -v $(pwd)/src/test/resources/education:/maps -v $(pwd)/neo4j:/neo4j --entrypoint /bin/sh parsley
docker run --rm --name parsley -v $(pwd)/src/test/resources/education:/maps parsley