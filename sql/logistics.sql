/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100432
 Source Host           : localhost:3306
 Source Schema         : logistics

 Target Server Type    : MySQL
 Target Server Version : 100432
 File Encoding         : 65001

 Date: 14/02/2025 16:10:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for banners
-- ----------------------------
DROP TABLE IF EXISTS `banners`;
CREATE TABLE `banners`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of banners
-- ----------------------------
INSERT INTO `banners` VALUES (2, 'Cung cấp giải pháp Logistics - Fulfillment toàn diện.', 'http://res.cloudinary.com/dfy5bqyi7/image/upload/v1739070573/pqrxtbbdghqrutc3yjfk.jpg', 'Welcome to ZTO');

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `main_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (7, '<p><em style=\"color: rgb(65, 65, 65);\">Bạn đang tìm kiếm dịch vụ vận chuyển hàng hóa từ Trung Quốc về Việt Nam? Hãy cùng chúng tôi khám phá chi tiết về dịch vụ vận chuyển Trung Quốc và những lợi ích mà nó mang lại. Đồng thời soituyet.vn cũng gợi ý các loại hình vận chuyển Trung - Việt phổ biến hiện nay.</em></p>', 'http://res.cloudinary.com/dfy5bqyi7/image/upload/v1739165580/kjfc2lmqdhkqy2854omn.png', 'Dịch Vụ Vận Chuyển Trung Việt');

-- ----------------------------
-- Table structure for subtitle
-- ----------------------------
DROP TABLE IF EXISTS `subtitle`;
CREATE TABLE `subtitle`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `subtitle` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `post_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK5ofm08pjni5ea4y9rx6gub1xx`(`post_id` ASC) USING BTREE,
  CONSTRAINT `FK5ofm08pjni5ea4y9rx6gub1xx` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of subtitle
-- ----------------------------
INSERT INTO `subtitle` VALUES (51, 'http://res.cloudinary.com/dfy5bqyi7/image/upload/v1739165583/bklvb6oh4bn4vbx4l0jb.png', '<ol><li>Các loại hình vận chuyển Trung Quốc về Việt Nam</li></ol><p><span style=\"color: rgb(65, 65, 65);\">Trung Quốc và Việt Nam là hai quốc gia láng giềng, có mối quan hệ hợp tác kinh tế, thương mại phát triển. Do đó, nhu cầu vận chuyển hàng hóa giữa hai nước ngày càng tăng cao. Hiện nay, có 3 loại hình vận chuyển Trung Quốc về Việt Nam tại soituyet.vn:</span></p><ul><li>Vận chuyển bằng đường bay</li><li><span style=\"color: rgb(65, 65, 65);\">Hình thức này thường đi nguyên cont biển từ xưởng hoặc các cảng nổi tiếng như Thiên Tân, Thanh Đảo, Ninh Ba, Quảng Châu… về Việt Nam. Vận chuyển Trung Quốc về Việt Nam phù hợp và thuận tiện nếu khách đi nguyên cont đường biển vì tiết kiệm được phí nội địa, phù hợp với các mặt hàng có khối lượng lớn, số lượng hàng hoá nhiều.</span></li></ul>', 7);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birth_date` date NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` enum('ADMIN','USER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
