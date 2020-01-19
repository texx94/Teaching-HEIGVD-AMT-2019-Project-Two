import csv
import random

def generateMovieIds():
    movieIds = set()
    for x in range(0, 100):
        movieIds.add(random.randint(1, 185077))
    return movieIds

movie_data_file = open("../../microservices/movie-server/src/main/resources/data.sql", "w")
movie_data_file.write("USE movie;\n")
movie_data_file.write("BEGIN;\n")

print("Creating commands for adding movies to the database...")
with open('movies.csv') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=';')
    for row in csv_reader:
        movie_data_file.write(f'INSERT INTO movie (title, year) VALUES ("{row[0]}", "{row[1]}");\n')
    
print("Done!")

movie_data_file.write("COMMIT;\n")
movie_data_file.write("BEGIN;\n")

print("Creating commands for adding users to the database...")
userCounter = 1
with open('users.csv') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=';')
    for row in csv_reader:
        # Add the user to the database
        movie_data_file.write(f'INSERT INTO user (lastname, firstname) VALUES ("{row[0]}", "{row[1]}");\n')

        # Generate some movies that he has seen
        for movieId in generateMovieIds():
            movie_data_file.write(f'INSERT INTO movie_watched (fk_user, fk_movie) VALUES ({userCounter}, {movieId});\n')
        userCounter += 1


print("Done!")

movie_data_file.write("COMMIT;\n")
movie_data_file.close()
