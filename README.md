# Java Assignments Overview

This workspace contains three separate Java assignments. Each one reads CSV data, processes it with a different set of rules, and writes report files into its own `Outputs` folder.

## Assignment 1: TV Show Dataset Reports

Location: `Assignment 1/`

This assignment reads `TMDB_tv_dataset_v3_clean.csv` and produces several reports about TV shows. The program filters and sorts the dataset to generate:

- the top 50 shows by rating
- the top 50 shows for each original language
- the number of shows with more than 15,000 votes
- the top 50 shows released between 2010 and 2019
- the top 50 shows that are still in production
- the top 50 one-word titles ranked by popularity
- the top 50 shows for each genre

Generated files are written under `Assignment 1/Outputs/Tasks`, `Assignment 1/Outputs/Languages`, and `Assignment 1/Outputs/Genres`.

## Assignment 2: Sales and Inventory Reports

Location: `Assignment 2/`

This assignment loads three CSV files containing items, customers, and purchases. It builds relationships between those records and generates retail-style reports:

- top selling items
- top customers by total spending
- sales totals by category
- purchase history for each customer
- updated inventory after purchases

The data model is centered around items, customers, and purchases, and the reports are printed to the console.

## Assignment 3: Employee Salary Reports

Location: `Assignment 3/`

This assignment reads employee data from separate CSV files for managers, workers, interns, freelancers, and sales staff. It calculates salary using the rules defined in each employee type and writes ranking reports:

- top 10 employees by salary overall
- top 10 employees by salary within each employee type
- top 10 employees by salary within each department

It also demonstrates the `Employee.Builder` pattern by creating sample employee objects in `Main`.

## Input and Output Structure

- Assignment 1 reads from the TV dataset and writes report CSVs into the `Outputs` subfolders.
- Assignment 2 reads from `Assignment 2/src/inputs/` and prints its reports to the console.
- Assignment 3 reads from `Assignment 3/src/Inputs/` and writes report CSVs into `Assignment 3/src/Outputs/`.

## Notes

- Each assignment is independent and has its own entry-point class.
- The CSV parsing is tailored to the specific structure of each assignment’s input files.
