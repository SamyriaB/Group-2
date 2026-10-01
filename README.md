# Group-2
Team Project CEN 4010

## Database setup (hosted on Railway \ PostgreSQL)

Schema is in `database/schema.sql` and dummy data in `database/seed.sql`.
These gonna be applied to our live PostgreSQL database hosted on Railway.

### Running the app
1. Pull `main`, then click **Sync Maven Changes** (pops up at the top right), or open the Maven tab on the right and click reload.
2. In IntelliJ: click the **GeektextApplication** dropdown (top right) - **Edit Configurations** - **Modify options** - **Environment variables**, and paste there:
```
   DB_URL=jdbc:postgresql://HOST:PORT/railway;DB_USERNAME=postgres;DB_PASSWORD=...
```
You can find DB_URL, DB_USERNAME and DB_PASSWORD on our WhatsApp chat.
3. Run the app. `HikariPool-1 - Start completed` in the console means it connected. You can also run it from IntelliJ top right,
where it says GeekTextApplication and near it green run button.

### Working on your feature
1. Make sure you have the latest `main`:
```
   git checkout main
   git pull
```
2. Create your own branch for your feature (like `feature/shopping-cart`, `feature/wishlist` etc.):
```
   git checkout -b feature/your-feature-name
```
3. Write your code and commit:
```
   git add .
   git commit -m "Short description of what you did"
```
4. Push your branch:
```
   git push -u origin feature/your-feature-name
```
5. Open a **Pull Request** on GitHub into `main` and ask team to review.
6. After its approved and merged, go back to step 1 for your next task.