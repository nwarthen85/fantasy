package fantasySchedualMaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author U344341
 *
 */
/**
 * @author U344341
 *
 */
public class SchedualMaker {
	private List<Integer> listForDivisionEast = new ArrayList<Integer>();
	private List<Integer> listForDivisionWest = new ArrayList<Integer>();
	private List<Integer> listForDivisionNorth = new ArrayList<Integer>();
	private ArrayList<Integer> divisionalWeeks = new ArrayList<Integer>();
	private Map<Integer, List<String>> weekCheckLists = new HashMap<Integer, List<String>>();

	private String te1;
	private String te2;
	private String te3;
	private String te4;
	private String tw1;
	private String tw2;
	private String tw3;
	private String tw4;
	private String ts1;
	private String ts2;
	private String ts3;
	private String ts4;
	
	public SchedualMaker() {
		
	}
	
	/**
	 * @return
	 * the piece that ties everything together just like doc browns flux capacitor for the delorian time machine
	 */
	public Map<String, List<MatchUp>> fluxCapacitor() {
		listForDivisionEast = new ArrayList<Integer>();
		listForDivisionWest = new ArrayList<Integer>();
		listForDivisionNorth = new ArrayList<Integer>();
		divisionalWeeks = new ArrayList<Integer>();
		weekCheckLists = new HashMap<Integer, List<String>>();
		if (listForDivisionEast.isEmpty()) {
			generateTeams();
		}
		Map<String, List<List<Integer>>> matchUpsEast = generateDivisionalMatchups(listForDivisionEast, "EAST");
		Map<String, List<List<Integer>>> matchUpsWest = generateDivisionalMatchups(listForDivisionWest, "WEST");
		Map<String, List<List<Integer>>> matchUpsNorth = generateDivisionalMatchups(listForDivisionNorth, "NORTH");
		Map<String, List<List<Integer>>> divisionalMatchups = new HashMap<String, List<List<Integer>>>();
		divisionalMatchups.put("EAST",matchUpsEast.get("EAST"));
		divisionalMatchups.put("WEST",matchUpsWest.get("WEST"));
		divisionalMatchups.put("NORTH",matchUpsNorth.get("NORTH"));
		Map<String, List<MatchUp>> map = generateNonDivisionalMatchups(divisionalMatchups);
		ArrayList<Integer> nonDivisionalWeeks = new ArrayList<Integer>();
		for (int i=1; i<13; i++) {
			if (!divisionalWeeks.contains(i)) {
				nonDivisionalWeeks.add(i);
			}
		}
		List<Integer> teamList = new ArrayList<Integer>();
		teamList.addAll(listForDivisionEast);
		teamList.addAll(listForDivisionWest);
		teamList.addAll(listForDivisionNorth);
		return addNonDivWeeksToMatchupsHelper(map, nonDivisionalWeeks);
		
	}
	
	/**
	 * @param list
	 * @return
	 * sorts matchups according to the week the matchup takes place
	 */
	public List<MatchUp> sortByWeek(List<MatchUp> list) {
		List<MatchUp> newList = new ArrayList<MatchUp>();
		int count = 1;
		while (count!=13) {
			for (MatchUp m:list) {
				if (m.getWeekCode().equals(count)) {
					newList.add(m);
					count++;
				}
			}
		}
		return newList;
	}
	
	/**
	 * @param teamMatchupsMap
	 * gives a printout of division weeks, whos in what division, and each teams schedual
	 */
	public void printOutByPlayer(Map<Integer, List<MatchUp>> teamMatchupsMap) {
		System.out.println("                                           TD's AND BEER");
		System.out.println("                                          FANTASY FOOTBALL");
		System.out.println("______Divisional Weeks______");
		Collections.sort(divisionalWeeks);
		for (int x:divisionalWeeks) {
			System.out.println(x);
		}
		System.out.println();
		System.out.println("______Divisions______");
		Map<Integer, String> nameMap = putplayersInDivision();
		System.out.println("EAST: "+nameMap.get(1)+" "+nameMap.get(2)+" "+nameMap.get(3)+" "+nameMap.get(4));
		System.out.println("WEST: "+nameMap.get(5)+" "+nameMap.get(6)+" "+nameMap.get(7)+" "+nameMap.get(8));
		System.out.println("NORTH: "+nameMap.get(9)+" "+nameMap.get(10)+" "+nameMap.get(11)+" "+nameMap.get(12));
		System.out.println();
		System.out.println("________Sheduals________");
		System.out.println();
		int count = 1;
		while (count<13) {
			System.out.println("______"+nameMap.get(teamMatchupsMap.get(count).get(0).getTeamCode())+"s shedual______");
			for (MatchUp m:teamMatchupsMap.get(count)) {
				System.out.println(nameMap.get(m.getTeamCode())+" VS. "+nameMap.get(m.getOpponentCode())+" week "+m.getWeekCode()+" ");
			}
			count++;
			System.out.println();
			System.out.println();
		}
	}
	
	/**
	 * @param teamMatchupsMap
	 * gives a printout of division weeks, whos in what division, and each teams schedual
	 */
	public void printOutByWeek(Map<Integer, List<MatchUp>> teamMatchupsMap) {
		System.out.println("                                           TD's AND BEER");
		System.out.println("                                          FANTASY FOOTBALL");
		System.out.println("______Divisional Weeks______");
		Collections.sort(divisionalWeeks);
		for (int x:divisionalWeeks) {
			System.out.println(x);
		}
		System.out.println();
		System.out.println("______Divisions______");
		Map<Integer, String> nameMap = putplayersInDivision();
		System.out.println("EAST: "+nameMap.get(1)+" "+nameMap.get(2)+" "+nameMap.get(3)+" "+nameMap.get(4));
		System.out.println("WEST: "+nameMap.get(5)+" "+nameMap.get(6)+" "+nameMap.get(7)+" "+nameMap.get(8));
		System.out.println("NORTH: "+nameMap.get(9)+" "+nameMap.get(10)+" "+nameMap.get(11)+" "+nameMap.get(12));
		System.out.println();
		System.out.println("________Sheduals________");
		System.out.println();
		int count = 1;
		Map<Integer, List<MatchUp>> newMap = orderByWeek(teamMatchupsMap);
		while (count<13) {
			System.out.println("______"+"Week"+" "+count+" "+"Shedual______");
			List<String> names = new ArrayList<String>();
 			for (MatchUp m:newMap.get(count)) {
 				if (!names.contains(nameMap.get(m.getTeamCode())) || !names.contains(nameMap.get(m.getOpponentCode()))) {
 					System.out.println(nameMap.get(m.getTeamCode())+" VS. "+nameMap.get(m.getOpponentCode()));
 					names.add(nameMap.get(m.getTeamCode()));
 					names.add(nameMap.get(m.getOpponentCode()));
 				}
			}
			count++;
			System.out.println();
			System.out.println();
		}
	}
	
	public Map<Integer, List<MatchUp>> orderByWeek(Map<Integer, List<MatchUp>> teamMatchupsMap) {
		Map<Integer, List<MatchUp>> map  = new HashMap<Integer, List<MatchUp>>();
		for (int i=1;i<13;i++) {
			map.put(i, new ArrayList<MatchUp>());
		}
		int count = 1;
		while (count<13) {
			for (MatchUp m:teamMatchupsMap.get(count)) {
				map.get(m.getWeekCode()).add(m);
			}
			count++;
		}
		
		return map;
	}
	
	/**
	 * adds the team numbers to the global lists east 1-4 west 5-8 north 9-12
	 */
	public void generateTeams() {
		for(int i=1;i<5;i++) {
			listForDivisionEast.add(i);
		}
		for(int i=5;i<9;i++) {
			listForDivisionWest.add(i);
		}
		for(int i=9;i<13;i++) {
			listForDivisionNorth.add(i);
		}	
	}
	
	/**
	 * @param divisionTeamCodes
	 * @param division
	 * @return
	 * takes the division (east, west, or north) and the numbers associated with that division and creates all possible combinations 
	 * for division matchups then adds them to a list which goes in a map where the division is the key
	 */
	public Map<String, List<List<Integer>>> generateDivisionalMatchups(List<Integer> divisionTeamCodes, String division) {
		List<List<Integer>> comboList = new ArrayList<List<Integer>>();
		Map<String, List<List<Integer>>> divMatchupMap = new HashMap<String, List<List<Integer>>>();
		divisionalWeeks = randomWeekGeneratorForDivMatchups(12, 6);
		//sets up who plays who
		for (Integer i=0; i<=divisionTeamCodes.size(); i++) {
			for (int j=i+1; j<=divisionTeamCodes.size()-1; j++) {
				List<Integer> combo = new ArrayList<Integer>();
				combo.add(0, divisionTeamCodes.get(i));
				combo.add(1, divisionTeamCodes.get(j));
				comboList.add(combo);
				comboList.add(combo);
			}
		}
		if (division.equalsIgnoreCase("EAST")) {
			divMatchupMap.put("EAST", comboList);
		}
		if (division.equalsIgnoreCase("WEST")) {
			divMatchupMap.put("WEST", comboList);
		}
		if (division.equalsIgnoreCase("NORTH")) {
			divMatchupMap.put("NORTH", comboList);
		}
		
		return divMatchupMap;
	}
	
	public Map<String, List<MatchUp>> generateNonDivisionalMatchups(Map<String, List<List<Integer>>> divisionlTeamMatchups) {
		
		Map<String, List<List<Integer>>> combinations = getNonDivisionCombos(listForDivisionEast, listForDivisionNorth, listForDivisionWest);
		combinations.get("EAST").addAll(divisionlTeamMatchups.get("EAST"));
		combinations.get("WEST").addAll(divisionlTeamMatchups.get("WEST"));
		combinations.get("NORTH").addAll(divisionlTeamMatchups.get("NORTH"));
		Map<String, List<List<Integer>>> organizedDivComboMap = organizeMatchups(combinations);
		
		Map<String, List<MatchUp>> matchUpMap = new HashMap<String, List<MatchUp>>();
		matchUpMap.put("EAST", matchupMaker(organizedDivComboMap.get("EAST"), listForDivisionEast));
		matchUpMap.put("WEST", matchupMaker(organizedDivComboMap.get("WEST"), listForDivisionWest));
		matchUpMap.put("NORTH", matchupMaker(organizedDivComboMap.get("NORTH"), listForDivisionNorth));
		setTeamOpponDivisions(matchUpMap);
		
		for (MatchUp m:matchUpMap.get("EAST")) {
			m.setLeageMatchups(matchUpMap);
		}
		for (MatchUp m:matchUpMap.get("WEST")) {
			m.setLeageMatchups(matchUpMap);
		}
		for (MatchUp m:matchUpMap.get("NORTH")) {
			m.setLeageMatchups(matchUpMap);
		}
		
		addWeeksToMatchups(matchUpMap, "EAST", 1, 4, listForDivisionEast);
		addWeeksToMatchups(matchUpMap, "WEST", 5, 8, listForDivisionWest);
		addWeeksToMatchups(matchUpMap, "NORTH", 9, 12, listForDivisionNorth);
		
		return matchUpMap;
	}
	
	public void setTeamOpponDivisions(Map<String, List<MatchUp>> matchUpMap) {
		for (MatchUp m:matchUpMap.get("EAST")) {
			m.setTeamDivision("EAST");
			if (m.getOpponentCode()<=4) {
				m.setOpponentDivision("EAST");
			} else if (m.getOpponentCode()>4&&m.getOpponentCode()<=8) {
				m.setOpponentDivision("WEST");
			} else {
				m.setOpponentDivision("NORTH");
			}
		}
		for (MatchUp m:matchUpMap.get("WEST")) {
			m.setTeamDivision("WEST");
			if (m.getOpponentCode()<=4) {
				m.setOpponentDivision("EAST");
			} else if (m.getOpponentCode()>4&&m.getOpponentCode()<=8) {
				m.setOpponentDivision("WEST");
			} else {
				m.setOpponentDivision("NORTH");
			}
		}
		for (MatchUp m:matchUpMap.get("NORTH")) {
			m.setTeamDivision("NORTH");
			if (m.getOpponentCode()<=4) {
				m.setOpponentDivision("EAST");
			} else if (m.getOpponentCode()>4&&m.getOpponentCode()<=8) {
				m.setOpponentDivision("WEST");
			} else {
				m.setOpponentDivision("NORTH");
			}
		}
	}
	
	public List<MatchUp> matchupMaker(List<List<Integer>> divCombos, List<Integer> teams) {
		List<MatchUp> list = new ArrayList<MatchUp>();
		for (List<Integer> combo:divCombos) {
			if (teams.contains(combo.get(1))) {
				MatchUp matchup = new MatchUp(combo.get(0), combo.get(1), -1, true);
				list.add(matchup);
			} else {
				MatchUp matchup = new MatchUp(combo.get(0), combo.get(1), -1, false);
				list.add(matchup);
			}
		}
		return list;
	}
	
	
	public Map<String, List<MatchUp>> addWeeksToMatchups(Map<String, List<MatchUp>> matchupMap, String div, int lowerTeamCode, int higherTeamCode, List<Integer> teamList) {
		List<MatchUp> divList = new ArrayList<MatchUp>();
		for (MatchUp matchup:matchupMap.get(div)) {
			if (matchup.getOpponentCode()>=lowerTeamCode&&matchup.getOpponentCode()<=higherTeamCode) {
				divList.add(matchup);
			}
		}
		Map<Integer, List<MatchUp>> map = new HashMap<Integer, List<MatchUp>>();
		for (int team:teamList) {
			List<MatchUp> matchupList = new ArrayList<MatchUp>();
			for (MatchUp matchup:divList) {
				if (matchup.getTeamCode()==team) {
					matchupList.add(matchup);
				}
			}
			map.put(team, matchupList);
		}
		addDivWeeksToMatchupsHelper(map);
		
		return matchupMap;
	}
	
	public void addDivWeeksToMatchupsHelper(Map<Integer, List<MatchUp>> matchUpMap) {
		int week = -1;
		for (int team:matchUpMap.keySet()) {
			for (MatchUp matchup:matchUpMap.get(team)){
				if (matchup.getWeekCode().equals(-1)) {
						if (isTeamPlayingThisWeek(matchUpMap.get(team), divisionalWeeks.get(0)) == true || isTeamPlayingThisWeek(matchUpMap.get(matchup.getOpponentCode()), divisionalWeeks.get(0)) == true) {
							week = generateNewWeek(matchUpMap.get(team), matchUpMap.get(matchup.getOpponentCode()), divisionalWeeks);
						} else {
							week = divisionalWeeks.get(0);
						}
						matchup.setWeekCode(week);
						loop:
						for (MatchUp opponentMatchup:matchUpMap.get(matchup.getOpponentCode())) {
							if (opponentMatchup.getOpponentCode()==matchup.getTeamCode() && opponentMatchup.getWeekCode().equals(-1)) {
								opponentMatchup.setWeekCode(week);
								break loop;
							}
						}
					}
				}
		}
	}
	
	public Map<String, List<MatchUp>> addNonDivWeeksToMatchupsHelper(Map<String, List<MatchUp>> matchupMap, ArrayList<Integer> nonDivisionalWeeks) {
		List<MatchUp> mList = new ArrayList<MatchUp>();
		for (MatchUp m:matchupMap.get("EAST")) {
			if (m.getWeekCode().equals(-1)) {
				mList.add(m);
			}
		}
		for (MatchUp m:matchupMap.get("WEST")) {
			if ((m.getWeekCode().equals(-1)&&!m.getOpponentCode().equals(1))
					&&(m.getWeekCode().equals(-1)&&!m.getOpponentCode().equals(2))
					&&(m.getWeekCode().equals(-1)&&!m.getOpponentCode().equals(3))
					&&(m.getWeekCode().equals(-1)&&!m.getOpponentCode().equals(4))) {
				mList.add(m);
			}
		}
		
		Map<Integer, List<MatchUp>> weekMap = new HashMap<Integer, List<MatchUp>>();
		for (Integer i=0;i<6;i++) {
			List<MatchUp> list = new ArrayList<MatchUp>();
			List<String> weekList = new ArrayList<String>();
			weekMap.put(i, list);
			weekCheckLists.put(i, weekList);
		}
		
		for (MatchUp m:mList) {
			putMatchupInMap(weekMap, m);
		}
		for (int key:weekMap.keySet()) {
			for (MatchUp m:weekMap.get(key)) {
				m.setWeekCode(nonDivisionalWeeks.get(key));
				for (MatchUp mu:matchupMap.get(m.getOpponentDivision())) {
					if (m.getTeamCode().equals(mu.getOpponentCode())&&m.getOpponentCode().equals(mu.getTeamCode())) {
						mu.setWeekCode(nonDivisionalWeeks.get(key));
					}
				}
			}
		}
		
		return matchupMap;
	}
	
	private void putMatchupInMap(Map<Integer, List<MatchUp>> map, MatchUp m) {
		boolean addM = false;
		int swapCount = 0;
		while (addM==false) {
			swapCount++;
			if (swapCount>500) {
				fluxCapacitor();
			}
			int matchCount = 0;
			Random rand = new Random();
			for (int key: map.keySet()) {
				if (map.get(key).isEmpty()) {
					map.get(key).add(m);
					weekCheckLists.get(key).add(m.getTeamCode().toString());
					weekCheckLists.get(key).add(m.getOpponentCode().toString());
					return;
				} 
			}
			
			
			List<MatchUp> listForCopy = new ArrayList<MatchUp>();
			loop:
			for (int key: map.keySet()) {
				listForCopy = map.get(key);
				if (weekCheckLists.get(key).contains(m.getTeamCode().toString())||weekCheckLists.get(key).contains(m.getOpponentCode().toString())||map.get(key).size()==6) {
					matchCount++;
				} 
				if (matchCount==0) {
					addM = true;
					weekCheckLists.get(key).add(m.getTeamCode().toString());
					weekCheckLists.get(key).add(m.getOpponentCode().toString());
					break loop;
				}
				matchCount = 0;
			}
			
			if (addM==true) {
				listForCopy.add(m);
				return;
			}
			MatchUp matchup = null;
			int key = rand.nextInt(6);
			boolean makeSwap = false;
			int findSwapCount = 0;
			while (makeSwap==false) { 
				findSwapCount++;
				if (findSwapCount==500) {
					fluxCapacitor();
				}
				matchup = null;
				key = rand.nextInt(6);
				loop:
				for (int i=0;i<map.get(key).size();i++) {
					if (m.getTeamCode().equals(map.get(key).get(i).getTeamCode())||m.getTeamCode().equals(map.get(key).get(i).getOpponentCode())
							||m.getOpponentCode().equals(map.get(key).get(i).getTeamCode())||m.getOpponentCode().equals(map.get(key).get(i).getOpponentCode())) {
						matchup = map.get(key).get(i);
						weekCheckLists.get(key).remove(matchup.getTeamCode().toString());
						weekCheckLists.get(key).remove(matchup.getOpponentCode().toString());
						if (!weekCheckLists.get(key).contains(m.getTeamCode().toString())&&!weekCheckLists.get(key).contains(m.getOpponentCode().toString())) {
							map.get(key).remove(matchup);
							makeSwap=true;
							break loop;
						} else {
							weekCheckLists.get(key).add(matchup.getTeamCode().toString());
							weekCheckLists.get(key).add(matchup.getOpponentCode().toString());
						}
					} 
				}
			}
			if (makeSwap==true) {
				weekCheckLists.get(key).add(m.getTeamCode().toString());
				weekCheckLists.get(key).add(m.getOpponentCode().toString());
				map.get(key).add(m);
				m = matchup;
			}
		}
	}
	
	/*
	 * generates a list of all combinations of nondivision games then seperates them into division
	 */
	public Map<String, List<List<Integer>>>  getNonDivisionCombos(List<Integer> east, List<Integer> north, List<Integer> west) {
		Random rand = new Random();
		List<List<Integer>> leagueList = new ArrayList<List<Integer>>();
		int r = rand.nextInt(60)+1;
		int NorthOrWest = rand.nextInt(10)+1;
		boolean high = false;
		boolean low = false;
		boolean inside = false;
		boolean outside = false;
		boolean splitHigh = false;
		boolean splitLow = false;
		boolean northBool = false;
		boolean westBool = false;
		if (r>0&&r<11) {
			splitLow = true;
		} else if (r>10&&r<21) {
			splitHigh = true;
		} else if (r>20&&r<31) {
			inside = true;
		} else if (r>30&&r<41) {
			outside = true;
		} else if (r>40&&r<51) {
			low = true;
		} else if (r>50&&r<61) {
			high = true;
		} 
		if (NorthOrWest>5) {
			northBool = true;
		} else {
			westBool = true;
		}
		
		System.out.println("r="+r+"westBool="+westBool+"northBool="+northBool+" "+"high="+high+" "+"low="+low+" "+"inside="+inside+" "+"outside="+outside+" "+"splitHigh="+splitHigh+" "+"splitLow="+splitLow);
		
		if (high==true&&westBool==true) {
			leagueList = getNonDivisionCombosHelper(2, 3, 0, 1, "WEST");
		} else if (low==true&&westBool==true) {
			leagueList = getNonDivisionCombosHelper(0, 1, 2, 3, "WEST");
		} else if (inside==true&&westBool==true) {
			leagueList = getNonDivisionCombosHelper(1, 2, 0, 3, "WEST");
		} else if (outside==true&&westBool==true) {
			leagueList = getNonDivisionCombosHelper(0, 3, 1, 2, "WEST");
		} else if (splitHigh==true&&westBool==true) {
			leagueList = getNonDivisionCombosHelper(0, 2, 1, 3, "WEST");
		} else if (splitLow==true&&westBool==true) {
			leagueList = getNonDivisionCombosHelper(1, 3, 0, 2, "WEST");
		} else if (high==true&&northBool==true) {
			leagueList = getNonDivisionCombosHelper(1, 0, 3, 2, "NORTH");
		} else if (low==true&&northBool==true) {
			leagueList = getNonDivisionCombosHelper(3, 2, 1, 0, "NORTH");
		} else if (inside==true&&northBool==true) {
			leagueList = getNonDivisionCombosHelper(3, 0, 2, 1, "NORTH");
		} else if (outside==true&&northBool==true) {
			leagueList = getNonDivisionCombosHelper(2, 1, 3, 0, "NORTH");
		} else if (splitHigh==true&&northBool==true) {
			leagueList = getNonDivisionCombosHelper(3, 1, 2, 0, "NORTH");
		} else if (splitLow==true&&northBool==true) {
			leagueList = getNonDivisionCombosHelper(2, 0, 3, 1, "NORTH");
		}
		List<List<Integer>> eastCombos = new ArrayList<List<Integer>>();
		List<List<Integer>> westCombos = new ArrayList<List<Integer>>();
		List<List<Integer>> northCombos = new ArrayList<List<Integer>>();
		for (List<Integer> combo:leagueList) {
			if (listForDivisionEast.contains(combo.get(0))) {
				eastCombos.add(combo);
			} else if (listForDivisionWest.contains(combo.get(0))) {
				westCombos.add(combo);
			} else {
				northCombos.add(combo);
			}
		}
			
		Map<String, List<List<Integer>>> divComboMap = new HashMap<String, List<List<Integer>>>();
		divComboMap.put("EAST", eastCombos);
		divComboMap.put("WEST", westCombos);
		divComboMap.put("NORTH", northCombos);
		return divComboMap;
	}
	
	public List<List<Integer>>  getNonDivisionCombosHelper(int a, int b, int c, int d, String div) {
		List<List<Integer>> leagueList = new ArrayList<List<Integer>>();
		if (div.equals("WEST"))	{
			for (int x:listForDivisionWest) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(0, listForDivisionEast.get(a));
				listOne.add(1, x);
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionEast.get(b));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			for (int x:listForDivisionNorth) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(0, listForDivisionEast.get(c));
				listOne.add(1, x);
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionEast.get(d));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			
			for (int x:listForDivisionEast) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(x);
				listOne.add(1, listForDivisionWest.get(a));
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionWest.get(b));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			for (int x:listForDivisionNorth) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(x);
				listOne.add(1, listForDivisionWest.get(c));
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionWest.get(d));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			
			for (int x:listForDivisionEast) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(x);
				listOne.add(1, listForDivisionNorth.get(a));
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionNorth.get(b));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			for (int x:listForDivisionWest) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(x);
				listOne.add(1, listForDivisionNorth.get(c));
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionNorth.get(d));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			} 
		} else {
			for (int x:listForDivisionWest) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(0, listForDivisionEast.get(d));
				listOne.add(1, x);
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionEast.get(c));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			for (int x:listForDivisionNorth) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(0, listForDivisionEast.get(b));
				listOne.add(1, x);
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionEast.get(a));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			
			for (int x:listForDivisionEast) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(x);
				listOne.add(1, listForDivisionWest.get(d));
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionWest.get(c));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			for (int x:listForDivisionNorth) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(x);
				listOne.add(1, listForDivisionWest.get(b));
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionWest.get(a));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			
			for (int x:listForDivisionEast) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(x);
				listOne.add(1, listForDivisionNorth.get(d));
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionNorth.get(c));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
			for (int x:listForDivisionWest) {
				List<Integer> listOne = new ArrayList<Integer>();
				listOne.add(x);
				listOne.add(1, listForDivisionNorth.get(b));
				leagueList.add(listOne);
				List<Integer> listTwo = new ArrayList<Integer>();
				listTwo.add(0, listForDivisionNorth.get(a));
				listTwo.add(1, x);
				leagueList.add(listTwo);
			}
		}
		return sortLeagueList(leagueList);
	}
	
	private List<List<Integer>> sortLeagueList(List<List<Integer>> leagueList) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		for (int i=1;i<13;i++) {
			for (List<Integer> combo:leagueList) {
				if (combo.contains(i)) {
					if (i!=combo.get(0)) {
						List<Integer> c = new ArrayList<Integer>();
						c.add(0, i);
						c.add(1,combo.get(0));
						list.add(c);
					} else {
						list.add(combo);
					}
				}
			}
		}
		List<Integer> remove = new ArrayList<Integer>();
		for (int i=0;i<list.size();i++) { 
			List<Integer> combo = list.get(i);
			int count = 0;
			for (int j=i;j<list.size();j++) {
				if (combo.get(0).equals(list.get(j).get(0))&&combo.get(1).equals(list.get(j).get(1))&&count!=2) {
					count++;
				} 
				if (combo.get(0).equals(list.get(j).get(0))&&combo.get(1).equals(list.get(j).get(1))&&count==2) {
					remove.add(j);
				}
			}
		}
		List<List<Integer>> newList = new ArrayList<List<Integer>>();
		for (int i=0;i<list.size();i++) {
			if (!remove.contains(i)) {
				newList.add(list.get(i));
			}
		}
		return newList;
	}
	
	public Map<String, List<List<Integer>>> organizeMatchups(Map<String, List<List<Integer>>> divComboMap) {
		Map<String, List<List<Integer>>> returnMap = new HashMap<String, List<List<Integer>>>();
		returnMap.put("EAST", organizeMatchupsHelper(divComboMap.get("EAST"), listForDivisionEast));
		returnMap.put("WEST", organizeMatchupsHelper(divComboMap.get("WEST"), listForDivisionWest));
		returnMap.put("NORTH", organizeMatchupsHelper(divComboMap.get("NORTH"), listForDivisionNorth));
		return returnMap;
		
		
	}
	
	public List<List<Integer>> organizeMatchupsHelper(List<List<Integer>> divCombos, List<Integer> teams) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		for (int i:teams) {
			for (List<Integer> combo:divCombos) {
				if (combo.contains(i)) {
					if (combo.get(0)==i) {
						list.add(combo);
					} else {
						List<Integer> organizedCombo = new ArrayList<Integer>();
						organizedCombo.add(0, combo.get(1));
						organizedCombo.add(1, combo.get(0));
						list.add(organizedCombo);
					}
				} 
			}
		}
		return list;
	}
		
	public boolean isTeamPlayingThisWeek(List<MatchUp> matchups, int week) {
		for (MatchUp matchup:matchups) {
			if (matchup.getWeekCode()!=-1) {
				if (matchup.getWeekCode()==week) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int generateNewWeek(List<MatchUp> team, List<MatchUp> opponent, List<Integer> weeks) {
		List<Integer> tw = new ArrayList<Integer>();
		List<Integer> ow = new ArrayList<Integer>();
		int validWeek = -1;
		for (MatchUp tm:team) {
			tw.add(tm.getWeekCode());
		}
		for (MatchUp om:opponent) {
			ow.add(om.getWeekCode());
		}
		for (int week:weeks) {
			if (!ow.contains(week) && !tw.contains(week)) {
				return week;
			}
		} 
		
		return validWeek;
	}
	
	/*
	 * generates a random week code to ensure every schedule is random
	 */
	public ArrayList<Integer> randomWeekGeneratorForDivMatchups(int max, int listSize) {
		Random rand = new Random();
		ArrayList<Integer> weeks = new ArrayList<Integer>();
		while (weeks.size()!=listSize) {
			int x = rand.nextInt(max);
			if (!weeks.contains(x)&&x!=0) {
				weeks.add(x);
			}
		}
		return weeks;
	}
	
	public Map<Integer, String> putplayersInDivision() {
		List<Integer> list = new ArrayList<Integer>(); 
		List<String> names = new ArrayList<String>(); 
		Map<Integer, String> map = new HashMap<Integer, String>(); 
		/*if everyone wants to choose cards for division uncomment this, put names in respect to cards chosen
		 * 10 lowest ace highest so for 1 - 4 it would be 10 - ace. Then comment 823 - 833*/
		
		//predetermined divisions
		map.put(1, te1);
		map.put(2, te2);
		map.put(3, te3);
		map.put(4, te4);
		map.put(5, tw1);
		map.put(6, tw2);
		map.put(7, tw3);
		map.put(8, tw4);
		map.put(9, ts1);
		map.put(10, ts2);
		map.put(11, ts3);
		map.put(12, ts4);

		/*names.add("Nick");
		names.add("Workman");
		names.add("Warthen");
		names.add("Kaleo");
		names.add("Smitley");
		names.add("Randy");
		names.add("vjjLicker");
		names.add("Dumas");
		names.add("Gary");
		names.add("Rachel");
		names.add("Timmy");
		names.add("tyeryar");
		
		
		//Randomizing divisions
		for (int i=1;i<13;i++) {
			list.add(i);
		}
		Random rand = new Random();
		while (!list.isEmpty()) {
			int x = rand.nextInt(list.size());
			int y = rand.nextInt(names.size());
			map.put(list.get(x), names.get(y));
			list.remove(x);
			names.remove(y);
		}*/
		return map;
		
	}
	
	public boolean checkFordivOpponentsPlayingBToBHelper(List<MatchUp> matchups) {
		int hold = 0;
		for (MatchUp m:matchups) {
			if (hold==m.getOpponentCode()) {
				return true;
			}
			hold = m.getOpponentCode();
		}
		return false;
	}
	
	public boolean checkForDivOpponentsPlayingBToB(Map<String, List<MatchUp>> teamScheduals, Map<Integer, List<MatchUp>> teamMatchupsMap) {
		for (int team:listForDivisionEast) {
			List<MatchUp> mList = new ArrayList<MatchUp>();
			for (MatchUp m:teamScheduals.get("EAST")) {
				if (m.getTeamCode().equals(team)) {
					mList.add(m);
				}
			}
			mList = new ArrayList<MatchUp>(sortByWeek(mList));
			if (checkFordivOpponentsPlayingBToBHelper(mList)==true) {
				return true;
			} else {
				teamMatchupsMap.put(team, mList);
			}
		}
		
		for (int team:listForDivisionWest) {
			List<MatchUp> mList = new ArrayList<MatchUp>();
			for (MatchUp m:teamScheduals.get("WEST")) {
				if (m.getTeamCode().equals(team)) {
					mList.add(m);
				}
			}
			mList = new ArrayList<MatchUp>(sortByWeek(mList));
			//mList = new ArrayList<MatchUp>(sortByWeek(mList));
			if (checkFordivOpponentsPlayingBToBHelper(mList)==true) {
				return true;
			} else {
				teamMatchupsMap.put(team, mList);
			}
		}
		
		for (int team:listForDivisionNorth) {
			List<MatchUp> mList = new ArrayList<MatchUp>();
			for (MatchUp m:teamScheduals.get("NORTH")) {
				if (m.getTeamCode().equals(team)) {
					mList.add(m);
				}
			}
			mList = new ArrayList<MatchUp>(sortByWeek(mList));
			//mList = new ArrayList<MatchUp>(sortByWeek(mList));
			if (checkFordivOpponentsPlayingBToBHelper(mList)==true) {
				return true;
			} else {
				teamMatchupsMap.put(team, mList);
			}
		}
		return false;
	}

	public String getTe1() {
		return te1;
	}

	public void setTe1(String te1) {
		this.te1 = te1;
	}

	public String getTe2() {
		return te2;
	}

	public void setTe2(String te2) {
		this.te2 = te2;
	}

	public String getTe3() {
		return te3;
	}

	public void setTe3(String te3) {
		this.te3 = te3;
	}

	public String getTe4() {
		return te4;
	}

	public void setTe4(String te4) {
		this.te4 = te4;
	}

	public String getTw1() {
		return tw1;
	}

	public void setTw1(String tw1) {
		this.tw1 = tw1;
	}

	public String getTw2() {
		return tw2;
	}

	public void setTw2(String tw2) {
		this.tw2 = tw2;
	}

	public String getTw3() {
		return tw3;
	}

	public void setTw3(String tw3) {
		this.tw3 = tw3;
	}

	public String getTw4() {
		return tw4;
	}

	public void setTw4(String tw4) {
		this.tw4 = tw4;
	}

	public String getTs1() {
		return ts1;
	}

	public void setTs1(String ts1) {
		this.ts1 = ts1;
	}

	public String getTs2() {
		return ts2;
	}

	public void setTs2(String ts2) {
		this.ts2 = ts2;
	}

	public String getTs3() {
		return ts3;
	}

	public void setTs3(String ts3) {
		this.ts3 = ts3;
	}

	public String getTs4() {
		return ts4;
	}

	public void setTs4(String ts4) {
		this.ts4 = ts4;
	}

	public ArrayList<Integer> getDivisionalWeeks() {
		return divisionalWeeks;
	}

	public void setDivisionalWeeks(ArrayList<Integer> divisionalWeeks) {
		this.divisionalWeeks = divisionalWeeks;
	}
}
