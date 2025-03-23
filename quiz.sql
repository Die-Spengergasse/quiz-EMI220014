create table q_questions
(
    q_id   int auto_increment
        primary key,
    q_text varchar(255) null
);

create table a_answers
(
    a_id      int auto_increment
        primary key,
    a_correct bit          null,
    a_text    varchar(255) null,
    fk_q_id   int          null,
    constraint FK85j4t9strub8hwfj1wk4eilc8
        foreign key (fk_q_id) references q_questions (q_id)
);

INSERT INTO `q_questions` VALUES (1,'What is the capital of France?'),(2,'What is a prime number?'),(3,'Which planet is known as the Red Planet?'),(4,'Who wrote the play \"Romeo and Juliet?\"'),(5,'What is the largest ocean on Earth?');

INSERT INTO `a_answers` VALUES (1,'\0','Moscow',1),(2,'','Paris',1),(3,'\0','Vienna',1),(4,'\0','Prague',1),(5,'','a number which is only divisible by 1 or itself',2),(6,'\0','a number not divisible by 2 or 3',2),(7,'\0','an odd number',2),(8,'\0','a number which is not divisible by itself',2),(9,'\0','Earth',3),(10,'','Mars',3),(11,'\0','Jupyter',3),(12,'\0','Venus',3),(13,'','William Shakespeare',4),(14,'\0','Charles Dickens',4),(15,'\0','Mark Twain',4),(16,'\0','Jane Austen',4),(17,'\0','Atlantic Ocean',5),(18,'\0','Indian Ocean',5),(19,'\0','Arctic Ocean',5),(20,'','Pacific Ocean',5);