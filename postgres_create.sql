CREATE TABLE "User_role" (
	"role_id" serial NOT NULL,
	"role_name" varchar(20) NOT NULL UNIQUE,
	CONSTRAINT User_role_pk PRIMARY KEY ("role_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "User" (
	"user_id" serial NOT NULL,
	"role_id" int NOT NULL DEFAULT '2',
	"login" varchar(12) NOT NULL UNIQUE,
	"password" varchar(80) NOT NULL,
	"locale_id" int NOT NULL,
	CONSTRAINT User_pk PRIMARY KEY ("user_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Order" (
	"order_id" serial NOT NULL,
	"user_id" int NOT NULL,
	"first_name" varchar(16) NOT NULL,
	"last_name" varchar(20) NOT NULL,
	"email" varchar(30) NOT NULL,
	"phone" varchar(20) NOT NULL,
	"bed_id" int NOT NULL,
	"type_id" int NOT NULL,
	"check_in" DATE NOT NULL,
	"check_out" DATE NOT NULL,
	"status_id" int NOT NULL,
	"room_number" int,
	"full_cost" numeric,
	CONSTRAINT Order_pk PRIMARY KEY ("order_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Room" (
	"room_id" serial NOT NULL,
	"type_id" int NOT NULL,
	"bed_id" int NOT NULL,
	"room_number" int NOT NULL UNIQUE,
	"room_price" numeric NOT NULL,
	"photo_id" bigint NOT NULL,
	CONSTRAINT Room_pk PRIMARY KEY ("room_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Room_type" (
	"type_id" serial NOT NULL,
	"type_name_en" varchar(15) NOT NULL UNIQUE,
	"type_name_ru" varchar(15) NOT NULL UNIQUE,
	CONSTRAINT Room_type_pk PRIMARY KEY ("type_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Room_bed" (
	"bed_id" serial NOT NULL,
	"bed_number" int NOT NULL UNIQUE,
	CONSTRAINT Room_bed_pk PRIMARY KEY ("bed_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Order_status" (
	"status_id" serial NOT NULL,
	"order_status_en" varchar(18) NOT NULL UNIQUE,
	"order_status_ru" varchar(18) NOT NULL UNIQUE,
	CONSTRAINT Order_status_pk PRIMARY KEY ("status_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Locale" (
	"locale_id" serial NOT NULL,
	"locale_name" varchar(2) NOT NULL UNIQUE,
	CONSTRAINT Locale_pk PRIMARY KEY ("locale_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "room_photo" (
	"photo_id" serial NOT NULL,
	"photo_img" bytea NOT NULL,
	"content_type" varchar(30) NOT NULL,
	"content_length" bigint NOT NULL,
	CONSTRAINT room_photo_pk PRIMARY KEY ("photo_id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "User" ADD CONSTRAINT "User_fk0" FOREIGN KEY ("role_id") REFERENCES "User_role"("role_id");
ALTER TABLE "User" ADD CONSTRAINT "User_fk1" FOREIGN KEY ("locale_id") REFERENCES "Locale"("locale_id");

ALTER TABLE "Order" ADD CONSTRAINT "Order_fk0" FOREIGN KEY ("user_id") REFERENCES "User"("user_id");
ALTER TABLE "Order" ADD CONSTRAINT "Order_fk1" FOREIGN KEY ("bed_id") REFERENCES "Room_bed"("bed_id");
ALTER TABLE "Order" ADD CONSTRAINT "Order_fk2" FOREIGN KEY ("type_id") REFERENCES "Room_type"("type_id");
ALTER TABLE "Order" ADD CONSTRAINT "Order_fk3" FOREIGN KEY ("status_id") REFERENCES "Order_status"("status_id");
ALTER TABLE "Order" ADD CONSTRAINT "Order_fk4" FOREIGN KEY ("room_number") REFERENCES "Room"("room_number");

ALTER TABLE "Room" ADD CONSTRAINT "Room_fk0" FOREIGN KEY ("type_id") REFERENCES "Room_type"("type_id");
ALTER TABLE "Room" ADD CONSTRAINT "Room_fk1" FOREIGN KEY ("bed_id") REFERENCES "Room_bed"("bed_id");
ALTER TABLE "Room" ADD CONSTRAINT "Room_fk2" FOREIGN KEY ("photo_id") REFERENCES "room_photo"("photo_id");






