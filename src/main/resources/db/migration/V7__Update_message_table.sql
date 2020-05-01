ALTER TABLE message DROP COLUMN tag;
ALTER TABLE message ADD receiver_id int8;
alter table if exists message
    add constraint message_reciever_fk
    foreign key (receiver_id) references usr;