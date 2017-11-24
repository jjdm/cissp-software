import requests
from bs4 import BeautifulSoup
from collections import namedtuple

Team = namedtuple('Team', 'name, odds')
Game = namedtuple('Game', 'home, away')
GamesTeams = namedtuple('GamesTeams', 'games, teams')
Result = namedtuple('Result', 'away, home, away_538, away_sg, away_avg, home_538, home_sg, home_avg, favored, odds, diff')

# 538 = WSH; SG = WAS
		
def get_text(node):
	if(not node or not node.string): return ''
	return str(node.string).strip()
	
def get_games_and_teams_from_538():
	teams = []
	games = []
	page = requests.get('https://projects.fivethirtyeight.com/2017-nfl-predictions/games/')
	soup = BeautifulSoup(page.content, 'html.parser')
	all_games = soup.find('div', { 'id' : 'games' })
	week = all_games.find('section', { 'class' : 'week' })
	print('538 ' + get_text(week.h3))
	games_table = week.find_all('table', { 'class' : 'game-body' })
	for g in games_table:
		tbody = g.tbody
		away_row = tbody.tr
		home_row = away_row.next_sibling
		away = away_row.find('td', { 'class' : 'logo' }).img['alt'].replace('-logo', '')
		home = home_row.find('td', { 'class' : 'logo' }).img['alt'].replace('-logo', '')	
		away = 'WAS' if away == 'WSH' else away 
		home = 'WAS' if home == 'WSH' else home
		away_odds = float(get_text(away_row.find('td', { 'class' : 'chance' })).replace('%', ''))
		home_odds = float(get_text(home_row.find('td', { 'class' : 'chance' })).replace('%', ''))
		games.append(Game(home, away))
		teams.append(Team(home, home_odds))
		teams.append(Team(away, away_odds))
	return GamesTeams(games, sorted(teams, key=lambda t: -t.odds))

def get_teams_from_sg():
	teams = []
	page = requests.get('http://www.survivorgrid.com/')
	soup = BeautifulSoup(page.content, 'html.parser')
	weeks = len(soup.find('div', { 'class' : 'week-nav' }).ul.find_all('li'))
	print(' SG Week ' + str(weeks))
	all_games = soup.find('table', { 'id' : 'grid' }).tbody
	rows = all_games.find_all('tr')
	for row in rows:
		columns = row.find_all('td')
		odds = get_text(columns[1]).replace('%', '')
		odds = float(odds) if odds else 0
		if(columns[3].span): columns[3].span.extract()
		team = get_text(columns[3])
		teams.append(Team(team, odds))
	return sorted(teams, key=lambda t: -t.odds)

games_teams_538 = get_games_and_teams_from_538()
games = games_teams_538.games
teams_538 = games_teams_538.teams
teams_sg = get_teams_from_sg()

results = []
for game in games:
	away_538 = next(t for t in teams_538 if t.name == game.away)
	away_sg = next(t for t in teams_sg if t.name == game.away)
	away_avg = (away_538.odds + away_sg.odds) / 2
	home_538 = next(t for t in teams_538 if t.name == game.home)
	home_sg = next(t for t in teams_sg if t.name == game.home)
	home_avg = (home_538.odds + home_sg.odds) / 2
	home_favored = home_avg >= away_avg
	favored = game.home + " (H)" if home_favored else game.away + " (A)"
	odds = home_avg if home_favored else away_avg
	diff = abs(home_avg - home_538.odds)
	result = Result(game.away, game.home, away_538.odds, away_sg.odds, away_avg, home_538.odds, home_sg.odds, home_avg, favored, odds, diff)
	results.append(result)

results = sorted(results, key=lambda r: -(r.odds - r.diff))
print("Away\tHome\tFavored\tSorted\tOdds\tDiff\t538-A\tSG-A\tAVG-A\t538-H\tSG-H\tAVG-H")
for r in results:
	print("{}\t{}\t{}\t{:.1f}\t{:.1f}\t{:.1f}\t{:.1f}\t{:.1f}\t{:.1f}\t{:.1f}\t{:.1f}\t{:.1f}".format(r.away, r.home, r.favored, (r.odds - r.diff), r.odds, r.diff, r.away_538, r.away_sg, r.away_avg, r.home_538, r.home_sg, r.home_avg))