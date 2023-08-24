package fantasySchedualMaker.ui;

import fantasySchedualMaker.MatchUp;
import fantasySchedualMaker.SchedualMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterface implements ActionListener {
    JTextField te1, te2, te3, te4, tw1, tw2, tw3, tw4, ts1, ts2, ts3, ts4;
    JLabel le1, le2, le3, le4, lw1, lw2, lw3, lw4, ls1, ls2, ls3, ls4;
    JButton b1;
    JFrame f1, f2;

    public UserInterface() {
        f1 = new JFrame();
        f1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                titleAlign(f1);
            }
        });
        te1 = new JTextField();
        te1.setBounds(50, 50, 150, 20);
        te2 = new JTextField();
        te2.setBounds(50, 100, 150, 20);
        te3 = new JTextField();
        te3.setBounds(50, 150, 150, 20);
        te4 = new JTextField();
        te4.setBounds(50, 200, 150, 20);

        tw1 = new JTextField();
        tw1.setBounds(400, 50, 150, 20);
        tw2 = new JTextField();
        tw2.setBounds(400, 100, 150, 20);
        tw3 = new JTextField();
        tw3.setBounds(400, 150, 150, 20);
        tw4 = new JTextField();
        tw4.setBounds(400, 200, 150, 20);

        ts1 = new JTextField();
        ts1.setBounds(800, 50, 150, 20);
        ts2 = new JTextField();
        ts2.setBounds(800, 100, 150, 20);
        ts3 = new JTextField();
        ts3.setBounds(800, 150, 150, 20);
        ts4 = new JTextField();
        ts4.setBounds(800, 200, 150, 20);

        b1 = new JButton("Generate Schedule");
        b1.setBounds(300, 250, 400, 50);
        b1.addActionListener(this);
        f1.add(te1);
        f1.add(te2);
        f1.add(te3);
        f1.add(te4);
        f1.add(b1);
        f1.add(tw1);
        f1.add(tw2);
        f1.add(tw3);
        f1.add(tw4);
        f1.add(ts1);
        f1.add(ts2);
        f1.add(ts3);
        f1.add(ts4);
        f1.setSize(1000, 1000);
        f1.setLayout(null);
        f1.setTitle("TD's AND BEER FANTAS FOOTBALL");
        f1.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        SchedualMaker sm = new SchedualMaker();
        sm.setTe1(te1.getText());
        sm.setTe2(te2.getText());
        sm.setTe3(te3.getText());
        sm.setTe4(te4.getText());
        sm.setTw1(tw1.getText());
        sm.setTw2(tw2.getText());
        sm.setTw3(tw3.getText());
        sm.setTw4(tw4.getText());
        sm.setTs1(ts1.getText());
        sm.setTs2(ts2.getText());
        sm.setTs3(ts3.getText());
        sm.setTs4(ts4.getText());
        f2 = new JFrame();

        boolean print = false;
        Map<String, List<MatchUp>> map = sm.fluxCapacitor();

        JLabel divWeeks = new JLabel("______Divisional Weeks______");
        divWeeks.setBounds(100, 0, 300, 20);
        int divWeeky = 0;
        for (Integer divWeek:sm.getDivisionalWeeks()) {
            divWeeky=divWeeky+20;
            JLabel divWeekLabel = new JLabel(divWeek.toString());
            divWeekLabel.setBounds(100, divWeeky, 150, 20);
            f2.add(divWeekLabel);
        }

        Map<Integer, List<MatchUp>> teamMatchupsMap = new HashMap<>();
        while (print == false) {
            if (sm.checkForDivOpponentsPlayingBToB(map, teamMatchupsMap) == false) {
                print = true;
            } else {
                map = new HashMap<>(sm.fluxCapacitor());
            }
        }
        Map<Integer, String> nameMap = sm.putplayersInDivision();
        JLabel eastDiv = new JLabel("______Eastern Division______");
        eastDiv.setBounds(100, 150, 200, 20);
        le1 = new JLabel(nameMap.get(1));
        le1.setBounds(100, 170, 150, 20);
        le2 = new JLabel(nameMap.get(2));
        le2.setBounds(100, 190, 150, 20);
        le3 = new JLabel(nameMap.get(3));
        le3.setBounds(100, 210, 150, 20);
        le4 = new JLabel(nameMap.get(4));
        le4.setBounds(100, 230, 150, 20);

        JLabel westDiv = new JLabel("______Western Division______");
        westDiv.setBounds(450, 150, 200, 20);
        lw1 = new JLabel(nameMap.get(5));
        lw1.setBounds(450, 170, 150, 20);
        lw2 = new JLabel(nameMap.get(6));
        lw2.setBounds(450, 190, 150, 20);
        lw3 = new JLabel(nameMap.get(7));
        lw3.setBounds(450, 210, 150, 20);
        lw4 = new JLabel(nameMap.get(8));
        lw4.setBounds(450, 230, 150, 20);

        JLabel southDiv = new JLabel("______Southern Division______");
        southDiv.setBounds(850, 150, 200, 20);
        ls1 = new JLabel(nameMap.get(9));
        ls1.setBounds(850, 170, 150, 20);
        ls2 = new JLabel(nameMap.get(10));
        ls2.setBounds(850, 190, 150, 20);
        ls3 = new JLabel(nameMap.get(11));
        ls3.setBounds(850, 210, 150, 20);
        ls4 = new JLabel(nameMap.get(12));
        ls4.setBounds(850, 230, 150, 20);
        int count = 1;
        int x = 50;
        int y = 300;

        f2.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                titleAlign(f2);
            }
        });

        Map<Integer, List<MatchUp>> newMap = sm.orderByWeek(teamMatchupsMap);
        while (count < 13) {
            if (count != 1 && (count - 1) % 4 == 0) {
                y = 300;
                x = x + 400;
            }
            y = y + 50;
            JLabel sheduleLabel = new JLabel("______" + "Week" + " " + count + " " + "Shedual______");
            sheduleLabel.setBounds(x, y, 300, 20);
            f2.add(sheduleLabel);
            List<String> names = new ArrayList<String>();
            for (MatchUp m : newMap.get(count)) {
                if (!names.contains(nameMap.get(m.getTeamCode())) || !names.contains(nameMap.get(m.getOpponentCode()))) {
                    JLabel label = new JLabel(nameMap.get(m.getTeamCode()) + "   VS.   " + nameMap.get(m.getOpponentCode()));
                    y = y + 10;
                    label.setBounds(x, y, 200, 20);
                    f2.add(label);
                    names.add(nameMap.get(m.getTeamCode()));
                    names.add(nameMap.get(m.getOpponentCode()));
                }
            }
            count++;
            System.out.println();
            System.out.println();
        }
        f2.setTitle("TD's AND BEER FANTAS FOOTBALL");
        f2.setSize(1500, 1000);
        f2.add(le1);
        f2.add(le2);
        f2.add(le3);
        f2.add(le4);
        f2.add(lw1);
        f2.add(lw2);
        f2.add(lw3);
        f2.add(lw4);
        f2.add(ls1);
        f2.add(ls2);
        f2.add(ls3);
        f2.add(ls4);
        f2.add(divWeeks);
        f2.add(eastDiv);
        f2.add(westDiv);
        f2.add(southDiv);
        f1.setVisible(false);
        f2.setLayout(null);
        f2.setVisible(true);
        sm.printOutByWeek(teamMatchupsMap);
        JButton regenerate = new JButton("Regenerate");
        regenerate.setBounds(50, y+50, 400, 50);
        regenerate.addActionListener(this);
        JButton exit = new JButton("Exit");
        exit.setBounds(450, y+50, 400, 50);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        f2.add(regenerate);
        f2.add(exit);
    }

    private void titleAlign(JFrame frame) {
        Font font = frame.getFont();
        String currentTitle = frame.getTitle().trim();
        FontMetrics fm = frame.getFontMetrics(font);
        int frameWidth = frame.getWidth();
        int titleWidth = fm.stringWidth(currentTitle);
        int spaceWidth = fm.stringWidth(" ");
        int centerPos = (frameWidth / 2) - (titleWidth / 2);
        int spaceCount = centerPos / spaceWidth;
        String pad = "";
        pad = String.format("%" + (spaceCount - 14) + "s", pad);
        frame.setTitle(pad + currentTitle);
    }

}
