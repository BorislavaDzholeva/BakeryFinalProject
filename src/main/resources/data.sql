INSERT INTO categories (id, name)
values (1,'White Bread'),
       (2, 'Black Bread'),
       (3, 'Special'),
       (4, 'Bread with sourdough');

INSERT INTO products(id, name, description, product_image, weight, price, allergens, availability, ingredients, category_id)
VALUES (1, 'Baguette', 'Bread inspired by the traditional French baguette.', '/images/p2RVPi1EdGy96ERJOUde.jpg', 350, 3.50, 'Gluten', 'Always', 'Wheat flour, Yeast, Less salt', 1),
       (2, 'Ciabatta', 'Classic Italian bread with a crunchy crust and it’s airy inside.', '/images/XrL7H8CIYUjTSbgkzesx.jpg', 300, 3.50,'Gluten', 'Always', 'Less salt', 1 ),
       (3, 'Ciabatta Mediterean', 'Italian ciabatta with dried tomatoes, olives and onions.', '/images/cdCzw2A5tX0Tkf1jXvOf.jpg', 300, 4.50,'Gluten', 'Always', 'Wheat flour, Yeast, Dried tomato, Olive, Onion, Less salt', 1 ),
       (4, 'Daily Bread', 'Classic white bread with tender and airy structure, crispy crust and neutral taste.', '/images/efxYacuN7B4Zxfuo4Jj9.jpg', 500, 2.50, 'Gluten', 'Always', 'Wheat flour, Yeast, Redused salt content', 1 ),
       (5, 'Maize', 'Corn flour bread with a very delicate core and a very fine crust.', '/images/EIt2ePSK6peuKNxPuzLn.jpg', 450, 2.50, 'Gluten, Milk', 'Always', 'Wheat flour, Corn flour, Yogurt, Sunflower oil, Yeast, Butter, Milk, Reduced salt content.', 1 ),
       (6, 'Village bread', 'Traditional Bulgarian bread with soft medium and gross, crunchy crust.', '/images/jBUSPkn6GdvmkXLpYX6Z.jpg', 500, 2.50, 'Gluten', 'Always', 'Wheat flour, Rye flour, Salt, Yeast', 1),
       (7, 'Bread with spelt', 'Spell bread with added wholegrain flour. The bread is soft inside and has thin, crunchy crust. It is mixed with a rich bouquet of seeds and nuts.', '/images/iaVlJv6d7Ndu9T9XgxvP.jpg', 450, 2.90, 'Gluten, Seeds', 'Always', 'Spell flour, Graham, Malt, Yeast, Flax seeds, Sunflower seeds, Oatmeal, Rye kernel', 2),
       (8, 'Multiseeds', 'Bread with a crunchy crust mixed with three types of flour with added sunflower seeds, oatmealq flaxseed and rye kernels.', '/images/qdbwqcaaY5LlczLL1TIr.jpg', 400, 3.90, 'Gluten, Seeds', 'Always', 'Wheat flour, Rye flour, Wholegrain flour, Linseed, Rye kernels, Oat meal, Sunflower seed, Less salt.', 2),
       (9, 'Soda bread', 'Full-bodied bread with two types of flour.', '/images/B4KhDaVDTlxnHym89Ioi.jpg', 300, 2.70, 'Butter, Gluten, Milk', 'Always', 'Wheat flour, Wholegrain flour, Soda, Sugar, Butter, Yogurt, Less salt', 2),
       (10, 'Tipov and rye bread', 'Tipov bread with rye flour added. The bread has a rich sprinkle of seeds.', '/images/Ka9QYI3Rininjp6LZXcj.jpg', 450, 3.00, 'Gluten, Seeds, Sesame', 'Weekend', 'Tipov flour, Rye flour, Oatmeal, Flax seeds, Pumpkin seeds, Corn flour, Sesame, Poppy seeds, Redused salt content', 2),
       (11, 'Tipov with walnuts', 'Tipov bread rich in walnuts with firm structure and crust', '/images/pZjU5junxrJbenGaYjRg.jpg', 450, 3.30, 'Gluten, Nuts', 'Weekend', 'Tipov flour, Rye flour, Yeast, Walnuts, Reduced salt content', 2),
 (12, 'Dr. Allison', 'Healthy bread made of wholegrain dutch flour whith preserved fibres and sesame sprinkle.', '/images/sTi18KySLVG08f1hasVZ.jpg', 450, 3.00, 'Gluten', 'Always', 'Dutch whole grain flour, sesame, yeast, redused salt.', 3),
(13, 'Low Gluten bread', 'Bread suitable for people on a gluten-free diet mixed with Buckwheat flour with seeds. Bread has s dense structure.', '/images/HNlLFjAefHf2Av0SWMje.jpg', 450, 5.15, 'Butter, Eggs, Seeds', 'Weekend', 'Corn flour, Buckwheat flour, Rice flour, Salt, Baking powder, Butter, Eggs, Cinnamon, Flax seeds, Sunflower seeds, Оаtmeal', 3),
(14, 'Spelt 100', 'Spell bread with cracked and crunchy crust and firm structure.', '/images/YYFZi8slZCjEXMzo9m4a.jpg', 350, 3.20, 'Gluten', 'Weekend','Spell flour, Salt, Yeast', 3),
(15, 'Tonus', 'The super food of the bakery. Suitable for people with healthy and energetic lifestyle.', '/images/A4MTFD3FpR7a61R1ovl9.jpg', 450, 4.20, 'Eggs, Gluten, Milk, Seeds','Always', 'Oatmeal, Flax seeds, Pumpkin seeds, Corn flour, Eggs, Milk, Baking powder, Cinnamon', 3),
(16, 'JoVan Sourdough', 'Bread with sourdough of three types of flour with predominantly wheat flour. It has a light structure and a thin, crunchy crust.', '/images/NWESci2Xivfv2wPEgmPe.jpg', 450, 3.60, 'Gluten','Always', 'Wheat flour, Rye flour, Wholegrain flour, Sourdought', 4),
(17, 'Rye sourdough', 'Rye bread with a thick structure and a crunchy crust. With a slight sour taste.', '/images/Y8qoSL6MvoSuD9W31xs4.jpg', 400, 3.00, 'Gluten', 'Always', 'Rye flour, Graham, Sourdought, Reduced salt content', 4),
(18, 'Sourdough Baguette', 'Baguette with three types of flour. It is airy inside and has crispy crust.', '/images/6X6bIoP9N7bRCn245pyV.jpg', 350, 2.90, 'Gluten', 'Always', 'Graham, Rye flour, Wheat flour, Sourdough, Salt', 4),
(19, 'Sourdough Dark', 'Aromatic whole grain bread with a light texture.', '/images/MxMwsZSj4qjvDgDM4ZXj.jpg', 450, 4.90, 'Gluten','Weekend', 'Wholegrain flour, Sourdough, Reduced salt content', 4);
