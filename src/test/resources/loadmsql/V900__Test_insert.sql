INSERT INTO player_price (player_id, season_id, month, price)
VALUES ('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2022/2023', 0, 900),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2022/2023', 1, 900),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2022/2023', 2, 950),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2022/2023', 3, 1000),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2022/2023', 4, 1180),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2022/2023', 5, 1300),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2022/2023', 6, 1590),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2022/2023', 7, 1000),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2022/2023', 8, 700),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2021/2022', 0, 700),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2021/2022', 1, 700),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2021/2022', 2, 750),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2021/2022', 3, 800),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2021/2022', 4, 730),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2021/2022', 5, 700),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2021/2022', 6, 790),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2021/2022', 7, 810),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', '2021/2022', 8, 900);

INSERT INTO change_owner (player_id, user_last_owner_id, user_new_owner_id, community_id, change_date)
VALUES ('2c3d0244-5388-44c8-a01f-6e7c26c55f81', 'fcf67544-9b8e-42d2-b5d1-c1f931b383bc', 'c0ad8f6f-f659-4c75-97d1-b7cb0b2fb21b', '982adeb4-f3d2-4e56-a558-9e51dfb4e3ae', NOW()),
('ecd7321c-6ec5-4451-b2bc-a1deaf12d2f1', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', '20e9f46c-5323-4305-8fbb-79aa29406828', NOW() + INTERVAL '1 day'),
('ecd7321c-6ec5-4451-b2bc-a1deaf12d2f1', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW()),
('ecd7321c-6ec5-4451-b2bc-a1deaf12d2f1', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW()),
('ecd7321c-6ec5-4451-b2bc-a1deaf12d2f1', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW()),
('ecd7321c-6ec5-4451-b2bc-a1deaf12d2f1', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW()),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW()),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW()),
('4ad8a8f3-2e2a-406e-8ced-470d1a3c00be', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW()),
('2797ee6e-580a-495e-8e2e-880b019b128c', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW()),
('2797ee6e-580a-495e-8e2e-880b019b128c', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW()),
('2797ee6e-580a-495e-8e2e-880b019b128c', 'f16bbe90-071b-4bad-8a8c-76555e3b0037', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', 'bc2c7992-d384-4065-a9d3-aa6cc59a9b9c', NOW());

INSERT INTO sale (season_id, community_id, user_owner_id, player_id, initial_bid_amount, transaction_status, created_at)
VALUES ('2021/2022', '982adeb4-f3d2-4e56-a558-9e51dfb4e3ae', '56fc94fa-2040-4e3b-a54a-c69ec26e77b2', '2c3d0244-5388-44c8-a01f-6e7c26c55f81', 300, 0, NOW());

INSERT INTO bid (user_bid_id, bid_amount, bid_status, sale_id, created_at)
VALUES ('81f25570-c20c-4902-abb2-8973d1798333', 350, 0, 1, NOW());
