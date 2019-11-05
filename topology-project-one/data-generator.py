from __future__ import print_function
import sys
import names
import random
from faker import Faker
fake = Faker()

print("use amtprojectone;")
print("INSERT INTO Viewer(Firstname, Lastname, Username, Genre, OwnerID) VALUES ")
nbIteration = int(sys.argv[1])
for i in xrange(nbIteration):
    firstname = names.get_first_name(gender='female')
    lastname = names.get_last_name()
    print("(\""+firstname+"\",\""+lastname+"\",\""+firstname+"."+lastname+"-"+str(i)+"\",\"Female\",\"1\"),")
for i in xrange(nbIteration):
    firstname = names.get_first_name(gender='male')
    lastname = names.get_last_name()
    print("(\""+firstname+"\",\""+lastname+"\",\""+firstname+"."+lastname+"-"+str(i)+"\",\"Male\",\"1\")",end='')
    if i != nbIteration-1:
        print(",")
    else:
        print(";")
        
print("INSERT INTO `Serie` (`Title`, `Producer`, `Synopsis`, `Genre`, `AgeRestriction`, `OwnerID`) VALUES ")
for i in xrange(nbIteration):
    title = "Sherlock-" + str(i) 
    producer = "BBC"
    synopsis = "blabla"
    genre = "thriller"
    ageRestriction = "12"
    ownerID = "1"
    print("(\""+title+"\",\""+producer+"\",\""+synopsis+"\",\""+genre+"\",\""+ageRestriction+"\",\""+ownerID+"\")",end='')
    if i != nbIteration-1:
        print(",")
    else:
        print(";")
        
print("INSERT INTO `WatchingInfo` (`IDSerie`, `IDViewer`, `TimeSpent`, `BeginningDate`, `OwnerID`) VALUES")
for i in xrange(nbIteration):
    for j in xrange(nbIteration):
        date = fake.date(pattern="%Y-%m-%d", end_datetime=None)
                                                                                
        print("(\""+str(i+1)+"\",\""+str(j+1)+"\",\""+str(random.randint(1,1000))+"\",\""+date+"\",\""+ownerID+"\")",end='')
        if j != nbIteration-1 or i != nbIteration-1:
            print(",")
        else:
            print(";")
        
