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

print("Creating commands for adding movies to the movie database...")
with open('movies.csv') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=';')
    for row in csv_reader:
        movie_data_file.write(f'INSERT INTO movie (title, year) VALUES ("{row[0]}", "{row[1]}");\n')

print("Done!")

movie_data_file.write("COMMIT;\n")
movie_data_file.write("BEGIN;\n")

print("Creating commands for adding users to the movie database...")
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

print("Creating commands for adding users to the auth database...")
auth_data_file = open("../../microservices/auth-server/src/main/resources/data.sql", "w")
auth_data_file.write("BEGIN;\n")
with open('users.csv') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=';')
    for row in csv_reader:
        # Add the user to the auth database
        auth_data_file.write(f'INSERT INTO user (lastname, firstname, email, password) VALUES ("{row[0]}", "{row[1]}", "{row[2]}", "$2a$10$db71I3CbNntNS0JqaGNI9OMRawCfuQJJf6oTDBLlYuMiWhdZ6Fxpy");\n')
print("Done!")
auth_data_file.write("COMMIT;\n")

print("Add specified admin and not admin users to the auth database...")
auth_data_file.write(f'INSERT INTO user (lastname, firstname, email, password, is_admin) VALUES ("Dupont", "Jean", "admin@admin.ch", "$2a$10$db71I3CbNntNS0JqaGNI9OMRawCfuQJJf6oTDBLlYuMiWhdZ6Fxpy", 1);\n')
auth_data_file.write(f'INSERT INTO user (lastname, firstname, email, password) VALUES ("Reis", "Mark", "notadmin@admin.ch", "$2a$10$db71I3CbNntNS0JqaGNI9OMRawCfuQJJf6oTDBLlYuMiWhdZ6Fxpy");\n')
print("Done!")
auth_data_file.close()
