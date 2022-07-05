INSERT INTO supplier_entity (id, supplier_id, name) VALUES (1, 'SUP0000001', 'SUPPLIER A');
INSERT INTO supplier_entity (id, supplier_id, name) VALUES (2, 'SUP0000002', 'SUPPLIER B');
INSERT INTO supplier_entity (id, supplier_id, name) VALUES (3, 'SUP0000003', 'SUPPLIER C');

INSERT INTO product_entity (id, name, product_id, price, category, supplier_entity_id) VALUES (1, 'DOMAINE CARNEROS'  , 'PRD0000008', 10,'WINE', 2);
INSERT INTO product_entity (id, name, product_id, price, category, supplier_entity_id) VALUES (2, 'BUDWEISER'         , 'PRD0000002', 10,'BEER', 2);
INSERT INTO product_entity (id, name, product_id, price, category, supplier_entity_id) VALUES (3, 'STELLA'            , 'PRD0000003', 10,'BEER', 3);
INSERT INTO product_entity (id, name, product_id, price, category, supplier_entity_id) VALUES (4, 'AMSTEL'            , 'PRD0000004', 10,'BEER', 1);
INSERT INTO product_entity (id, name, product_id, price, category, supplier_entity_id) VALUES (5, 'YELLOW TAIL'       , 'PRD0000005', 10,'WINE', 2);
INSERT INTO product_entity (id, name, product_id, price, category, supplier_entity_id) VALUES (6, 'MAYACAMAS'         , 'PRD0000006', 10,'WINE', 3);
INSERT INTO product_entity (id, name, product_id, price, category, supplier_entity_id) VALUES (7, 'MATTHIASSON'       , 'PRD0000007', 10,'WINE', 1);
