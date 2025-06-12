-- 设置数据库字符集
-- CREATE DATABASE IF NOT EXISTS train_tickets 
-- CHARACTER SET utf8mb4 
-- COLLATE utf8mb4_0900_ai_ci;

-- USE train_tickets;

-- 1. 乘客信息表
CREATE TABLE passengers (
    passenger_id INT AUTO_INCREMENT PRIMARY KEY,
    real_name VARCHAR(50) NOT NULL COMMENT '姓名',
    id_type ENUM('id_card', 'passport', 'other') NOT NULL DEFAULT 'id_card' COMMENT '证件类型',
    id_number VARCHAR(30) NOT NULL COMMENT '证件号码',
    phone VARCHAR(20) COMMENT '手机号',
    UNIQUE KEY uk_id_number (id_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='乘客基本信息';

-- 2. 车站信息表
CREATE TABLE stations (
    station_code VARCHAR(10) PRIMARY KEY COMMENT '车站代码',
    station_name VARCHAR(50) NOT NULL COMMENT '车站名称',
    city VARCHAR(50) NOT NULL COMMENT '所在城市',
    province VARCHAR(50) NOT NULL COMMENT '所在省份',
    INDEX idx_station_name (station_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车站信息';

-- 3. 车次信息表
CREATE TABLE trains (
    train_number VARCHAR(10) PRIMARY KEY COMMENT '车次号',
    train_type ENUM('G', 'D', 'C', 'Z', 'T', 'K', 'L','other') NOT NULL COMMENT '列车类型',
    start_station_code VARCHAR(10) NOT NULL COMMENT '始发站代码',
    end_station_code VARCHAR(10) NOT NULL COMMENT '终点站代码',
    departure_time TIME NOT NULL COMMENT '发车时间',
    arrival_time TIME NOT NULL COMMENT '到达时间',
    duration_minutes INT NOT NULL COMMENT '运行时长(分钟)',
    FOREIGN KEY (start_station_code) REFERENCES stations(station_code),
    FOREIGN KEY (end_station_code) REFERENCES stations(station_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车次信息';

-- 4. 车票信息表（主表）
CREATE TABLE tickets (
    ticket_id VARCHAR(21) PRIMARY KEY COMMENT '票号',
    passenger_id INT NOT NULL COMMENT '乘客ID',
    train_number VARCHAR(10) NOT NULL COMMENT '车次号',
    departure_station_code VARCHAR(10) NOT NULL COMMENT '出发站代码',
    arrival_station_code VARCHAR(10) NOT NULL COMMENT '到达站代码',
    departure_date DATE NOT NULL COMMENT '出发日期',
    departure_time TIME NOT NULL COMMENT '发车时间',
    arrival_time TIME NOT NULL COMMENT '到达时间',
    seat_type ENUM(
    'business',                  -- 商务座
    'premium',                   -- 特等座
    'first',                     -- 一等座
    'second',                    -- 二等座
    'sleeper',                   -- 动卧
    'deluxe_sleeper',            -- 高级动卧
    'first_sleeper',             -- 一等卧
    'second_sleeper',            -- 二等卧
    'deluxe_soft_sleeper',       -- 高级软卧
    'soft_sleeper',              -- 软卧
    'hard_sleeper',              -- 硬卧
    'compartment_hard_sleeper',  -- 包厢硬卧
    'soft_seat',                 -- 软座
    'hard_seat',                 -- 硬座
    'first_soft_seat',           -- 一等软座
    'second_soft_seat',          -- 二等软座
    'private_soft_compartment',  -- 一人软包
    'mixed_hard_seat',           -- 混编硬座
    'mixed_soft_seat',           -- 混编软座
    'mixed_hard_sleeper',        -- 混编硬卧
    'mixed_soft_sleeper',        -- 混编软卧
    'second_class_package',      -- 二等包座
    'multi_function',            -- 多功能座
    'no_seat'                    -- 无座
    ) NOT NULL COMMENT '席别/座位类型'，
    -- seat_type ENUM('business', 'first', 'second', 'soft_sleeper', 'hard_sleeper', 'hard_seat', 'no_seat') NOT NULL COMMENT '席别',
    seat_number VARCHAR(10) COMMENT '座位号',
    carriage_number VARCHAR(5) COMMENT '车厢号',
    ticket_price DECIMAL(10,2) NOT NULL COMMENT '票价',
    ticket_status ENUM('unused', 'used', 'refunded', 'changed') NOT NULL DEFAULT 'unused' COMMENT '票状态',
    qr_code_path VARCHAR(255) COMMENT '二维码图片路径',
    note TEXT COMMENT '备注',
    FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id),
    FOREIGN KEY (train_number) REFERENCES trains(train_number),
    FOREIGN KEY (departure_station_code) REFERENCES stations(station_code),
    FOREIGN KEY (arrival_station_code) REFERENCES stations(station_code),
    INDEX idx_departure_date (departure_date),
    INDEX idx_train_departure (train_number, departure_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车票信息';

-- 5. 退改签记录表
CREATE TABLE ticket_changes (
    change_id INT AUTO_INCREMENT PRIMARY KEY,
    original_ticket_id VARCHAR(20) NOT NULL COMMENT '原票号',
    new_ticket_id VARCHAR(20) COMMENT '新票号',
    change_type ENUM('refund', 'change', 'upgrade') NOT NULL COMMENT '变更类型',
    change_time DATETIME NOT NULL COMMENT '变更时间',
    change_fee DECIMAL(10,2) DEFAULT 0 COMMENT '手续费',
    reason VARCHAR(255) COMMENT '变更原因',
    FOREIGN KEY (original_ticket_id) REFERENCES tickets(ticket_id),
    FOREIGN KEY (new_ticket_id) REFERENCES tickets(ticket_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='票务变更记录';

-- 6. 火车票图片表
CREATE TABLE ticket_images (
    image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_id VARCHAR(20) NOT NULL COMMENT '关联的票号',
    image_path VARCHAR(255) NOT NULL COMMENT '图片存储路径',
    image_hash VARCHAR(64) COMMENT '图片哈希值(用于去重)',
    upload_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    is_verified BOOLEAN DEFAULT FALSE COMMENT '是否已验证',
    FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id),
    INDEX idx_ticket_id (ticket_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入示例数据
-- 车站数据
-- INSERT INTO stations (station_code, station_name, city, province) VALUES
-- ('BJX', '北京西', '北京', '北京'),
-- ('SHH', '上海虹桥', '上海', '上海'),
-- ('GZQ', '广州南', '广州', '广东'),
-- ('SZQ', '深圳北', '深圳', '广东'),
-- ('WHN', '武汉', '武汉', '湖北');

-- -- 车次数据
-- INSERT INTO trains (train_number, train_type, start_station_code, end_station_code, departure_time, arrival_time, duration_minutes) VALUES
-- ('G79', 'G', 'BJX', 'SZQ', '10:00:00', '18:35:00', 515),
-- ('D903', 'D', 'BJX', 'GZQ', '20:10:00', '06:50:00', 640),
-- ('G505', 'G', 'WHN', 'SHH', '08:30:00', '12:20:00', 230);

-- -- 乘客数据
-- INSERT INTO passengers (real_name, id_type, id_number, phone) VALUES
-- ('张三', 'id_card', '110101199001011234', '13800138000'),
-- ('李四', 'id_card', '310101199102022345', '13900139000');

-- -- 车票数据
-- INSERT INTO tickets (ticket_id, passenger_id, train_number, departure_station_code, arrival_station_code, departure_date, departure_time, arrival_time, seat_type, seat_number, carriage_number, ticket_price, purchase_time, ticket_status, qr_code_path) VALUES
-- ('E123456789', 1, 'G79', 'BJX', 'SZQ', '2023-12-25', '10:00:00', '18:35:00', 'second', '05F', '08', 538.50, '2023-12-20 14:25:00', 'unused', '/qrcodes/E123456789.png'),
-- ('D987654321', 2, 'D903', 'BJX', 'GZQ', '2023-12-28', '20:10:00', '06:50:00', 'soft_sleeper', '12', '05', 720.00, '2023-12-22 09:15:00', 'unused', '/qrcodes/D987654321.png');