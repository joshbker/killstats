# Simple Paper plugin to track kill related statistics, for specified worlds
Made for [dionyzauce](https://www.youtube.com/c/dionyzauce).

## Setup
1. Add plugin to server and start to generate `config.yml`
2. Stop the server and modify the `activeWorlds` array to contain the names of the worlds you wish to track kills in
3. Start the server again, all statistics will be saved to `profiles.yml`

# Accessing statistics via PlaceholderAPI
You can use the following placeholders via PlaceholderAPI to access statistics tracked:
- `killstats_kills`
- `killstats_deaths`
- `killstats_kdr`
- `killstats_killsreak`
- `killstats_best_killstreak`
