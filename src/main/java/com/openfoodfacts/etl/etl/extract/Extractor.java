package com.openfoodfacts.etl.etl.extract;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Extractor {
    public static void main(String[] args) {

        // 1️⃣ Création de la session Spark
        SparkSession spark = SparkSession.builder()
                .appName("OpenFoodFacts Extract")
                .master("local[*]")
                .config("spark.ui.enabled", "false")
                .getOrCreate();

        // 2️⃣ Chemin du fichier JSONL compressé
        String filePath = "data/openfoodfacts-products.jsonl.gz";

        // 3️⃣ Lecture du JSONL avec Spark
        Dataset<Row> products = spark.read()
                .format("json")
                .option("multiLine", true)
                .load(filePath);

        // 4️⃣ Affichage du schéma pour comprendre les colonnes
        System.out.println("=== Schéma des données ===");
        products.printSchema();

        // 5️⃣ Affichage des 10 premières lignes complètes
        System.out.println("=== 10 premiers produits ===");
        products.show(10, false);

        // 6️⃣ Optionnel : parcourir les lignes comme une liste
        System.out.println("=== Affichage via takeAsList ===");
        products.takeAsList(10).forEach(row -> {
            System.out.println(row);
        });

    }
}
