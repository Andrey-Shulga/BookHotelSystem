CREATE TABLE "User_role" (
	"role_id" serial NOT NULL,
	"role_name" varchar(20) NOT NULL UNIQUE,
	CONSTRAINT User_role_pk PRIMARY KEY ("role_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "User" (
	"user_id" serial NOT NULL,
	"role_id" int NOT NULL,
	"login" varchar(12) NOT NULL UNIQUE,
	"password" varchar(64) NOT NULL,
	CONSTRAINT User_pk PRIMARY KEY ("user_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Order" (
	"order_id" serial NOT NULL,
	"user_id" int NOT NULL,
	"first_name" varchar(16) NOT NULL,
	"last_name" varchar(20) NOT NULL,
	"email" varchar NOT NULL,
	"phone" varchar NOT NULL,
	"number_of_bed" int NOT NULL,
	"type_name" varchar NOT NULL,
	"check_in" DATE NOT NULL,
	"check_out" DATE NOT NULL,
	"order_status" varchar NOT NULL DEFAULT 'unconfirmed',
	CONSTRAINT Order_pk PRIMARY KEY ("order_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Room" (
	"room_id" serial NOT NULL,
	"type_id" int NOT NULL,
	"bed_id" int NOT NULL,
	"room_number" int NOT NULL UNIQUE,
	"room_status" varchar NOT NULL DEFAULT 'free',
	"room_price" numeric NOT NULL DEFAULT '0',
	CONSTRAINT Room_pk PRIMARY KEY ("room_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Room_type" (
	"type_id" serial NOT NULL,
	"type_name" varchar NOT NULL UNIQUE,
	CONSTRAINT Room_type_pk PRIMARY KEY ("type_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Room_bed" (
	"bed_id" serial NOT NULL,
	"bed_number" serial NOT NULL UNIQUE,
	CONSTRAINT Room_bed_pk PRIMARY KEY ("bed_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Room_photo" (
	"photo_id" serial NOT NULL,
	"type_id" int NOT NULL,
	"bed_id" int NOT NULL,
	"photo" bytea,
	CONSTRAINT Room_photo_pk PRIMARY KEY ("photo_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "Confirmation_order" (
	"confirmation_id" serial NOT NULL,
	"order_id" bigint NOT NULL,
	"room_id" int NOT NULL,
	CONSTRAINT Confirmation_order_pk PRIMARY KEY ("confirmation_id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "User" ADD CONSTRAINT "User_fk0" FOREIGN KEY ("role_id") REFERENCES "User_role"("role_id");

ALTER TABLE "Order" ADD CONSTRAINT "Order_fk0" FOREIGN KEY ("user_id") REFERENCES "User"("user_id");

ALTER TABLE "Room" ADD CONSTRAINT "Room_fk0" FOREIGN KEY ("type_id") REFERENCES "Room_type"("type_id");
ALTER TABLE "Room" ADD CONSTRAINT "Room_fk1" FOREIGN KEY ("bed_id") REFERENCES "Room_bed"("bed_id");



ALTER TABLE "Room_photo" ADD CONSTRAINT "Room_photo_fk0" FOREIGN KEY ("type_id") REFERENCES "Room_type"("type_id");
ALTER TABLE "Room_photo" ADD CONSTRAINT "Room_photo_fk1" FOREIGN KEY ("bed_id") REFERENCES "Room_bed"("bed_id");

ALTER TABLE "Confirmation_order" ADD CONSTRAINT "Confirmation_order_fk0" FOREIGN KEY ("order_id") REFERENCES "Order"("order_id");
ALTER TABLE "Confirmation_order" ADD CONSTRAINT "Confirmation_order_fk1" FOREIGN KEY ("room_id") REFERENCES "Room"("room_id");

