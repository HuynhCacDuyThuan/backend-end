-- phpMyAdmin SQL Dump
-- version 5.2.1deb3
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost:3306
-- Thời gian đã tạo: Th3 14, 2025 lúc 01:24 PM
-- Phiên bản máy phục vụ: 8.0.41-0ubuntu0.24.04.1
-- Phiên bản PHP: 8.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ZTO`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `banners`
--

CREATE TABLE `banners` (
  `id` bigint NOT NULL,
  `description` varchar(255) NOT NULL,
  `image_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `import_order`
--

CREATE TABLE `import_order` (
  `id` bigint NOT NULL,
  `cn_shipping_code` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `insurance_price` decimal(10,2) DEFAULT '0.00',
  `locked` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL,
  `package_numbers` int DEFAULT NULL,
  `package_unit_value` double DEFAULT NULL,
  `shipping_method` varchar(255) DEFAULT NULL,
  `vn_shipping_code` varchar(255) DEFAULT NULL,
  `line_id` bigint NOT NULL,
  `package_unit_id` bigint DEFAULT NULL,
  `status_id` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `import_order`
--

INSERT INTO `import_order` (`id`, `cn_shipping_code`, `created_date`, `insurance_price`, `locked`, `name`, `package_numbers`, `package_unit_value`, `shipping_method`, `vn_shipping_code`, `line_id`, `package_unit_id`, `status_id`, `user_id`) VALUES
(1, '', '2025-03-14 17:28:53', NULL, 1, 'Phụ kiện điện tử', NULL, NULL, '', '', 1, 2, 3, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `model`
--

CREATE TABLE `model` (
  `id` bigint NOT NULL,
  `active_flag` bit(1) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `delete_flag` bit(1) NOT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `model`
--

INSERT INTO `model` (`id`, `active_flag`, `created_by`, `created_date`, `delete_flag`, `modified_by`, `modified_date`, `name`) VALUES
(1, b'1', 'admin', '2025-03-06 10:06:34.000000', b'0', NULL, NULL, 'Line'),
(2, b'1', 'admin', '2025-03-06 10:06:48.000000', b'0', NULL, NULL, 'Đơn vị'),
(3, b'1', 'admin', '2025-03-06 10:07:00.000000', b'0', NULL, NULL, 'Trạng thái');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `model_detail`
--

CREATE TABLE `model_detail` (
  `id` bigint NOT NULL,
  `active_flag` bit(1) NOT NULL,
  `block` bit(1) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `delete_flag` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `model_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `model_detail`
--

INSERT INTO `model_detail` (`id`, `active_flag`, `block`, `created_by`, `created_date`, `delete_flag`, `name`, `model_id`) VALUES
(1, b'1', b'0', 'admin', '2025-03-14 17:26:06.238531', b'0', 'Điện tử', 1),
(2, b'1', b'0', 'admin', '2025-03-14 17:26:13.598398', b'0', 'kg', 2),
(3, b'1', b'0', 'admin', '2025-03-14 17:28:16.946169', b'0', 'Đang ở kho', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `posts`
--

CREATE TABLE `posts` (
  `id` bigint NOT NULL,
  `content` longtext NOT NULL,
  `main_image_url` varchar(500) DEFAULT NULL,
  `title` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `posts`
--

INSERT INTO `posts` (`id`, `content`, `main_image_url`, `title`) VALUES
(1, '<p><em style=\"color: rgb(65, 65, 65);\">Bạn đang tìm kiếm dịch vụ vận chuyển hàng hóa từ Trung Quốc về Việt Nam? Hãy cùng chúng tôi khám phá chi tiết về dịch vụ vận chuyển Trung Quốc và những lợi ích mà nó mang lại. Đồng thời soituyet.vn cũng gợi ý các loại hình vận chuyển Trung - Việt phổ biến hiện nay.</em></p>', 'http://res.cloudinary.com/dfy5bqyi7/image/upload/v1741953626/pm1m98rqfniwrkyfokou.png', 'vận chuyển Trung Quốc về Việt Nam');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `subtitle`
--

CREATE TABLE `subtitle` (
  `id` bigint NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `subtitle` longtext,
  `post_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `subtitle`
--

INSERT INTO `subtitle` (`id`, `image_url`, `subtitle`, `post_id`) VALUES
(1, 'http://res.cloudinary.com/dfy5bqyi7/image/upload/v1741953629/y3kklalvgh59tffhp9m8.png', '<ol><li><strong>các loại hình vận chuyển Trung Quốc về Việt Nam</strong></li></ol><p><span style=\"color: rgb(65, 65, 65);\">Trung Quốc và Việt Nam là hai quốc gia láng giềng, có mối quan hệ hợp tác kinh tế, thương mại phát triển. Do đó, nhu cầu vận chuyển hàng hóa giữa hai nước ngày càng tăng cao. Hiện nay, có 3 loại hình vận chuyển Trung Quốc về Việt Nam tại soituyet.vn:</span></p><ul><li><strong style=\"color: rgb(65, 65, 65);\">Vận chuyển bằng đường bay</strong></li></ul><p><span style=\"color: rgb(65, 65, 65);\">Đây là loại hình vận chuyển nhanh nhất, chỉ mất khoảng 2-3 ngày. Tuy nhiên, chi phí vận chuyển đường bay khá cao, chỉ phù hợp với các mặt hàng có giá trị lớn hoặc hàng gấp. Hơn hết vì đặc tính là láng giềng nên đường bay ít được dùng do giá thành khá cao và không được ưa chuộng.</span></p><ul><li><strong>Vận chuyển bằng đường biển</strong></li></ul><p>Hình thức này thường đi nguyên cont biển từ xưởng hoặc các cảng nổi tiếng như Thiên Tân, Thanh Đảo, Ninh Ba, Quảng Châu… về Việt Nam. Vận chuyển Trung Quốc về Việt Nam phù hợp và thuận tiện nếu khách đi nguyên cont đường biển vì tiết kiệm được phí nội địa, phù hợp với các mặt hàng có khối lượng lớn, số lượng hàng hoá nhiều.</p>', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `customer_code` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `sub` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `customer_code`, `email`, `name`, `phone_number`, `picture`, `sub`) VALUES
(1, 'ZTO-01', 'huynhduythuan668@gmail.com', 'Duythuan Huynh', NULL, 'https://lh3.googleusercontent.com/a/ACg8ocJc358S8zBnIGbK23gR9OFV6CSOWxypE8oDKimfby1gQI5-UQ=s96-c', '105240375290208597958'),
(2, NULL, 'denled3c@gmail.com', '3C Đèn Led', NULL, 'https://lh3.googleusercontent.com/a/ACg8ocL2d9c6x_LyFKGVvPfY33z5ktEn1VHIyBbwFs8_Haq5l8CuAwc=s96-c', '101243074284470140556'),
(3, NULL, 'tuandv.hut@gmail.com', 'Tuân Dương Văn', NULL, 'https://lh3.googleusercontent.com/a/ACg8ocJ07NG2JI25gUBzJ4ER9dXAdCnRSVpcoROV3Y0nnPFIp7XgS8Nm=s96-c', '113589999666727413443'),
(4, NULL, 'ztovietnam.vn@gmail.com', 'Việt Nam ZTO', NULL, 'https://lh3.googleusercontent.com/a/ACg8ocLDPVLac_sdvsIlGVfW0AsBWQaqBPMcQp9Bxds68uo5F6xXKO8=s96-c', '110018626181177645454'),
(6, NULL, '20130423@st.hcmuaf.edu.vn', 'Huỳnh Các Duy Thuần', NULL, 'https://lh3.googleusercontent.com/a/ACg8ocIKQLF7qEviiHR33AlZ3u-Lsp9JSNdNU_2B2P6unPf2n0wEUg=s96-c', '110102554677224787225'),
(7, '', 'phamvulong2411@gmail.com', 'Long Phạm', NULL, 'https://lh3.googleusercontent.com/a/ACg8ocJCRb_Pmw8ufB0KkFva4vKDlnso0uK_xZGp_KWZGv197Y9FAC1R=s96-c', '113614631690240913404');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `banners`
--
ALTER TABLE `banners`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `import_order`
--
ALTER TABLE `import_order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKisakaeoi7r6od9ejn62x9ybxo` (`line_id`),
  ADD KEY `FK8190pd7xj1d449q9f2gcbdo8x` (`package_unit_id`),
  ADD KEY `FKln0898vxvkou55m2bpg50qgf9` (`status_id`),
  ADD KEY `FK1nf5k71ax6dmkcvagqur1dwt1` (`user_id`);

--
-- Chỉ mục cho bảng `model`
--
ALTER TABLE `model`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `model_detail`
--
ALTER TABLE `model_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1f1wend6xm8d256rgoo2sfnkr` (`model_id`);

--
-- Chỉ mục cho bảng `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `subtitle`
--
ALTER TABLE `subtitle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5ofm08pjni5ea4y9rx6gub1xx` (`post_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `banners`
--
ALTER TABLE `banners`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `import_order`
--
ALTER TABLE `import_order`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `model`
--
ALTER TABLE `model`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `model_detail`
--
ALTER TABLE `model_detail`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `posts`
--
ALTER TABLE `posts`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `subtitle`
--
ALTER TABLE `subtitle`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `import_order`
--
ALTER TABLE `import_order`
  ADD CONSTRAINT `FK1nf5k71ax6dmkcvagqur1dwt1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK8190pd7xj1d449q9f2gcbdo8x` FOREIGN KEY (`package_unit_id`) REFERENCES `model_detail` (`id`),
  ADD CONSTRAINT `FKisakaeoi7r6od9ejn62x9ybxo` FOREIGN KEY (`line_id`) REFERENCES `model_detail` (`id`),
  ADD CONSTRAINT `FKln0898vxvkou55m2bpg50qgf9` FOREIGN KEY (`status_id`) REFERENCES `model_detail` (`id`);

--
-- Các ràng buộc cho bảng `model_detail`
--
ALTER TABLE `model_detail`
  ADD CONSTRAINT `FK1f1wend6xm8d256rgoo2sfnkr` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`);

--
-- Các ràng buộc cho bảng `subtitle`
--
ALTER TABLE `subtitle`
  ADD CONSTRAINT `FK5ofm08pjni5ea4y9rx6gub1xx` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
