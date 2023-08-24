package fantasySchedualMaker;

import java.util.List;
import java.util.Map;

public class MatchUp {
	private Integer teamCode;
	private Integer opponentCode;
	private Integer weekCode;
	private boolean division;
	private String teamDivision;
	private String opponentDivision;
	private List<Integer> nonDivWeeks;
	private Map<String, List<MatchUp>> leageMatchups;
	
	public MatchUp(int teamCode, int opponentCode, Integer weekCode, boolean division) {
		this.teamCode = teamCode;
		this.opponentCode = opponentCode;
		this.weekCode = weekCode;
	}
	
	public MatchUp() {
		
	}

	public Integer getOpponentCode() {
		return opponentCode;
	}

	public void setOpponentCode(int opponentCode) {
		this.opponentCode = opponentCode;
	}

	public Integer getWeekCode() {
		return weekCode;
	}

	public void setWeekCode(Integer weekCode) {
		this.weekCode = weekCode;
	}

	public Integer getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(int teamCode) {
		this.teamCode = teamCode;
	}

	public boolean isDivision() {
		return division;
	}

	public void setDivision(boolean division) {
		this.division = division;
	}

	public Map<String, List<MatchUp>> getLeageMatchups() {
		return leageMatchups;
	}

	public void setLeageMatchups(Map<String, List<MatchUp>> leageMatchups) {
		this.leageMatchups = leageMatchups;
	}

	public String getTeamDivision() {
		return teamDivision;
	}

	public void setTeamDivision(String teamDivision) {
		this.teamDivision = teamDivision;
	}

	public String getOpponentDivision() {
		return opponentDivision;
	}

	public void setOpponentDivision(String opponentDivision) {
		this.opponentDivision = opponentDivision;
	}

	public List<Integer> getNonDivWeeks() {
		return nonDivWeeks;
	}

	public void setNonDivWeeks(List<Integer> nonDivWeeks) {
		this.nonDivWeeks = nonDivWeeks;
	}

}
