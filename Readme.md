
Prerequisites
==============
a)aws cli
b)docker
c)Create a repository in aws-ecr for This project.If the repository already exist no need to create.
d)after create repository click the 'view push command' option then u will find push commands.
e)Set aws-Iam credentials in bitbucket-environment variables or AccountVariables.



==========================
pushing the image into aws
==========================
1)build a image 

2)push the image into aws repository

#


==============================
pulling the image from aws-ecr
==============================
1)enter the "aws configure" command via command prompt.
2)fill the details.
3)enter "aws ecr get-login" command.Then u will get docker login Token that is valid 12hours only.
4)login the docker by using docker-login token.
5)pull the image from aws-ecr