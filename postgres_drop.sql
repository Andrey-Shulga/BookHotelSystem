ALTER TABLE "User" DROP CONSTRAINT IF EXISTS "User_fk0";

ALTER TABLE "User" DROP CONSTRAINT IF EXISTS "User_fk1";

ALTER TABLE "Order" DROP CONSTRAINT IF EXISTS "Order_fk0";

ALTER TABLE "Order" DROP CONSTRAINT IF EXISTS "Order_fk1";

ALTER TABLE "Order" DROP CONSTRAINT IF EXISTS "Order_fk2";

ALTER TABLE "Order" DROP CONSTRAINT IF EXISTS "Order_fk3";

ALTER TABLE "Order" DROP CONSTRAINT IF EXISTS "Order_fk4";

ALTER TABLE "Room" DROP CONSTRAINT IF EXISTS "Room_fk0";

ALTER TABLE "Room" DROP CONSTRAINT IF EXISTS "Room_fk1";

ALTER TABLE "Room" DROP CONSTRAINT IF EXISTS "Room_fk2";

DROP TABLE IF EXISTS "User_role";

DROP TABLE IF EXISTS "User";

DROP TABLE IF EXISTS "Order";

DROP TABLE IF EXISTS "Room";

DROP TABLE IF EXISTS "Room_type";

DROP TABLE IF EXISTS "Room_bed";

DROP TABLE IF EXISTS "Order_status";

DROP TABLE IF EXISTS "Locale";

DROP TABLE IF EXISTS "room_photo";

