# File Operation on pdf
--------------------------------

 RESTful API's:
 
 Database: inMemory H2
 ![image](https://user-images.githubusercontent.com/10458982/194951210-986dd5d4-c45c-4134-8315-22fe2fd1d4c0.png)


1. Upload PDF Document:
![image](https://user-images.githubusercontent.com/10458982/194950954-6c3a53a0-79cf-48eb-995c-af87df40529f.png)

--pdf file saved in DB--
![image](https://user-images.githubusercontent.com/10458982/194951379-3625a239-c496-49c3-b2f9-a9bcfdf8b205.png)

--comment assosiated with document--
![image](https://user-images.githubusercontent.com/10458982/194951529-1b13a18c-bdd7-488e-aa11-0c98adbf0f34.png)

![image](https://user-images.githubusercontent.com/10458982/194951971-52a37c6e-b2a6-4e72-810d-adacd55a768d.png)


2. View file 
![image](https://user-images.githubusercontent.com/10458982/194951732-f8a07fc6-33cf-424e-bad8-865c84df1746.png)

3. Find file with given id
![image](https://user-images.githubusercontent.com/10458982/194951836-fa25f5d2-1acd-427a-b7d9-9e0ac6ec823c.png)

4. Find list of files
![image](https://user-images.githubusercontent.com/10458982/194952377-8a8012a4-2867-442b-8715-0ce3dda31a46.png)

![image](https://user-images.githubusercontent.com/10458982/194952408-a3247fde-b88a-4291-9eb8-d09377972525.png)

![image](https://user-images.githubusercontent.com/10458982/194952454-9657b0d1-9eca-473c-852d-c01cc174e27b.png)

5. Delete file 
![image](https://user-images.githubusercontent.com/10458982/194952599-133033a0-abc3-4d94-9f2f-2de36619c032.png)

![image](https://user-images.githubusercontent.com/10458982/194952642-f919ec66-ce29-457c-b040-0e025cf6f76f.png)

![image](https://user-images.githubusercontent.com/10458982/194952736-4b713c0e-959b-4e8b-adc9-aadc1d25e27b.png)

--note* : on delete file associated comment will be removed--
![image](https://user-images.githubusercontent.com/10458982/194952880-470339c0-0b3d-4e80-8561-e6f536386b41.png)

6. Updating comment associated with document with comment id
--before update--
![image](https://user-images.githubusercontent.com/10458982/194953443-dfb9b9d0-fe87-40c4-b34b-d2f54e832015.png)

--after update--
![image](https://user-images.githubusercontent.com/10458982/194953554-8b724995-968b-49df-8a7e-9d45e64c18ee.png)

![image](https://user-images.githubusercontent.com/10458982/194953680-cb11de2f-afbd-437e-8f67-8517985da290.png)


*------Points------*

-> Independent RestFull CRUD API's are designed for Post
-> Unit & Integration testing performed
![image](https://user-images.githubusercontent.com/10458982/194954058-8527ea8a-595c-4e65-81b1-4c971ead9a7c.png)

-> Code Coverage with JaCoCO are performed
![image](https://user-images.githubusercontent.com/10458982/194954258-26348722-e72a-4ff3-bc83-c6457135ed2a.png)

-> OpenFeign has been integrated (link: https://github.com/karthikpavan/FeignClientforFileOperation)
![image](https://user-images.githubusercontent.com/10458982/194954803-bb434c0c-6904-462c-bae8-7f8c8c03dc24.png)

-> URL

http://localhost:4040/api/file/v1/storeFile

http://localhost:4040/api/file/v1/viewFile/2

http://localhost:4040/api/file/v1/findFile/2

http://localhost:4040/api/file/v1/files

http://localhost:4041/feignFile/delete/12

http://localhost:4040/api/post/v1/update/3

-> application will run on 4040 port restricted to localhost

-> Project designed in Java8 Spring using Gradle as build tool with other latest technologies included

*---Pending---*

->  with apologies, due to time restricition didn't implemented :( 







