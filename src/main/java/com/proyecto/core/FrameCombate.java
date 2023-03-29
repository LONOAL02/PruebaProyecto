package com.proyecto.core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class FrameCombate extends JFrame implements ActionListener {

    public static Historia h = new Historia();

    private FrameMapa frameMapa;
    DecimalFormat format1 = new DecimalFormat("#");

    public int nivelI = (int) h.pj.getNivel();


    private JLabel playerLabel;
    private JLabel enemyLabel;
    private JLabel playerHealthLabel;
    private JLabel enemyHealthLabel;
    private JTextArea playerDescription;
    private JTextArea enemyDescription;
    private JTextArea textoMenu;
    private JButton attackButton;
    private JButton healthButton;
    private JButton seeButton;
    private JButton useButton;
    private JButton exitButton;
    private JButton continueButton;
    private JComboBox<String> attacksComboBox;
    private JComboBox<String> healthComboBox;
    private JComboBox<String> inventoryComboBox;
    private JTextArea logTextArea;


    private float playerHealth=h.pj.getVida();
    private float enemyHealth=h.enemy.getVida();



    public static Historia save(Historia h1){
        h = h1;
        return h;
    }

    public static Historia update(Historia h1){
        h1 = h;
        return h1;
    }


    private static class JTextAreaWithBackground extends JTextArea {

        private Image background;

        public JTextAreaWithBackground() {
            background = new ImageIcon("src//main//java/com/proyecto/imagenes/fondo.jpg").getImage();
            setOpaque(false); // make the JTextArea transparent
        }


        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            super.paintComponent(g);
        }
    }

    private static class JPanelWithBackground extends JPanel {

        private Image background;

        public JPanelWithBackground() {
            background = new ImageIcon("src//main//java/com/proyecto/imagenes/fondo2.jpg").getImage();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            super.paintComponent(g);
        }
    }

    private static class JPanelWithBackground2 extends JPanel {

        private Image background;

        public JPanelWithBackground2() {
            background = new ImageIcon(FrameCombate.h.enemy.getImgPath()).getImage();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            super.paintComponent(g);
        }
    }

    public FrameCombate(FrameMapa frameMapa) {
        this.frameMapa = frameMapa;

        this.setTitle("Magic and beyond");

        this.setUndecorated(true);
        this.setLocationRelativeTo(null);

        // Tamaño de la ventana
        this.setSize(new Dimension(1280, 720));

        // Tamaño minimo de la ventana
        this.setMinimumSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear los paneles de los PJs
        JPanel playerPanel = new JPanelWithBackground();
        playerPanel.setPreferredSize(new Dimension(280,540));
        playerPanel.setLayout(new BorderLayout());
        playerLabel = new JLabel(h.pj.nombre, JLabel.CENTER);
        playerHealthLabel = new JLabel("HP: " + format1.format(playerHealth), JLabel.CENTER);
        playerDescription = new JTextArea();
        playerDescription.setEditable(false);
        playerDescription.setOpaque(false);
        playerDescription.setText(h.pj.toString()+"\nMana: "+h.pj.getMana());
        playerPanel.add(playerDescription,BorderLayout.CENTER);
        playerPanel.add(playerLabel, BorderLayout.NORTH);
        playerPanel.add(playerHealthLabel, BorderLayout.SOUTH);
        add(playerPanel, BorderLayout.WEST);

        // Crear los paneles de los Enemigos
        JPanel opponentPanel = new JPanelWithBackground2();
        opponentPanel.setPreferredSize(new Dimension(280,540));
        opponentPanel.setLayout(new BorderLayout());
        enemyLabel = new JLabel(h.enemy.nombre, JLabel.CENTER);
        enemyHealthLabel = new JLabel("HP: " + format1.format(enemyHealth), JLabel.CENTER);
        enemyDescription = new JTextArea();
        enemyDescription.setLineWrap(true);
        enemyDescription.setWrapStyleWord(true);
        enemyDescription.setEditable(false);
        enemyDescription.setOpaque(false);
        enemyDescription.setText(h.enemy.toString());
        opponentPanel.add(enemyDescription,BorderLayout.CENTER);
        opponentPanel.add(enemyLabel, BorderLayout.NORTH);
        opponentPanel.add(enemyHealthLabel, BorderLayout.SOUTH);
        add(opponentPanel, BorderLayout.EAST);

        // Crear el panel de ataque
        JPanel attackPanel = new JPanelWithBackground();
        attackPanel.setOpaque(false);
        attacksComboBox = new JComboBox<>(new String[]{"Cuerpo (90% Daño x1)", "Piernas (70% Daño x2)", "Cabeza (50% Daño x3)"});
        attackPanel.add(attacksComboBox, BorderLayout.CENTER);
        attackButton = new JButton("Atacar");
        attackButton.addActionListener(this);
        attackPanel.add(attackButton, BorderLayout.EAST);
        add(attackPanel, BorderLayout.SOUTH);

        // Crear el panel de vida
        JPanel healthPanel = new JPanel();
        healthPanel.setOpaque(false);
        healthComboBox = new JComboBox<>(new String[]{"Viales de 50.","Viales de 100.","Viales de 200."});
        healthPanel.add(healthComboBox);
        healthButton = new JButton("Curar");
        healthButton.addActionListener(this);
        healthPanel.add(healthButton);
        attackPanel.add(healthPanel);

        // Crear el panel de inventario
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setOpaque(false);
        inventoryComboBox = new JComboBox<>(h.inventary.obtenerNombresComoArray());
        inventoryPanel.add(inventoryComboBox);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        seeButton = new JButton("Ver");
        useButton = new JButton("Usar");
        seeButton.addActionListener(this);
        useButton.addActionListener(this);
        panelBotones.add(seeButton);
        panelBotones.add(useButton);
        inventoryPanel.add(panelBotones);
        healthPanel.add(inventoryPanel);

        //Crear el panel de menú
        JPanel menuPanel = new JPanel(new BorderLayout());
        JPanel textPanel = new JPanel();
        menuPanel.setOpaque(false);
        textPanel.setOpaque(false);
        textoMenu = new JTextArea("Forefathers One And All! Bear Witness!");
        textoMenu.setOpaque(false);
        textoMenu.setEditable(false);
        textoMenu.setAlignmentX(CENTER_ALIGNMENT);
        textoMenu.setAlignmentY(CENTER_ALIGNMENT);
        textPanel.add(textoMenu,BorderLayout.CENTER);
        JPanel panelSalida = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSalida.setOpaque(false);
        exitButton = new JButton("Salir");
        continueButton = new JButton("Continuar");
        JButton huirButton = new JButton("Huir");
        huirButton.addActionListener(this);
        if (FrameCombate.h.inventary.comprobarInventario(FrameCombate.h.reliq.abrigoDeSombras)){
            huirButton.setEnabled(true);
        }else {
            huirButton.setEnabled(false);
        }
        continueButton.setEnabled(false);
        exitButton.addActionListener(this);
        continueButton.addActionListener(this);
        panelSalida.add(continueButton);
        panelSalida.add(huirButton);
        panelSalida.add(exitButton);
        menuPanel.add(textPanel,BorderLayout.WEST);
        menuPanel.add(panelSalida,BorderLayout.CENTER);
        inventoryPanel.add(menuPanel);

        try {
            // Cargar la fuente desde el archivo OTF
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/com/proyecto/fonts/Mantinia Regular.otf")).deriveFont(12f);

            // Registrar la fuente personalizada en el entorno de gráficos
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            // Obtener el nombre de la fuente cargada
            String fontName = customFont.getFamily();

            // Crear el área de registro de ataques
            logTextArea = new JTextAreaWithBackground();
            logTextArea.setEditable(false);
            logTextArea.setLineWrap(true);
            logTextArea.setWrapStyleWord(true);
            textoMenu.setFont(new Font(fontName, Font.ITALIC, 20));
            logTextArea.setFont(new Font(fontName, Font.PLAIN, 20));
            playerLabel.setFont(new Font(fontName, Font.PLAIN, 18));
            enemyLabel.setFont(new Font(fontName, Font.PLAIN, 18));
            playerDescription.setFont(new Font(fontName, Font.PLAIN, 20));
            enemyDescription.setFont(new Font(fontName, Font.PLAIN, 20));
            enemyHealthLabel.setFont(new Font(fontName, Font.PLAIN, 20));
            playerHealthLabel.setFont(new Font(fontName, Font.PLAIN, 20));
            JScrollPane scrollPane = new JScrollPane(logTextArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            add(scrollPane, BorderLayout.CENTER);
        }catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


        // Añadir todo al JFrame
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        logTextArea.append("Te encuentras con "+h.enemy.nombre+"\n");


        if (FrameCombate.h.inventary.comprobarInventario(FrameCombate.h.reliq.mascaraDemoníaca)){
            enemyHealth -= 100;
            logTextArea.append("\nHaces 100 de daño gracias a la Máscara Demoníaca" + "!\n");
            enemyHealthLabel.setText("HP: " + format1.format(enemyHealth));
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == attackButton) {
            if (FrameCombate.h.arma.getDaño() == 0) {
                logTextArea.append("\nEquipa un arma primero" + "!\n");
            }else {
                String attack = (String) attacksComboBox.getSelectedItem();
                int precision = calculateDamage(attack);
                float daño = h.combate.atacar(precision, h.pj.calcularAtaque(), h.arma.getDaño());
                enemyHealth -= daño;
                if (FrameCombate.h.inventary.comprobarInventario(FrameCombate.h.reliq.espírituIndomable)) {
                    enemyHealth -= daño;
                    logTextArea.append("\nAtacas 2 veces gracias a Espiritu Indomable" + "!\n");
                    logTextArea.append("El oponente " + enemyLabel.getText() + " recibió " + format1.format(daño) + " de daño.\n");
                }
                if (enemyHealth <= 0) {
                    enemyHealth = 0;
                    enemyHealthLabel.setText("HP: " + format1.format(enemyHealth));
                    logTextArea.append("Enemigo derrotado!\n");
                    h.pj.setXp((int) (h.pj.getXp()+h.enemy.getNivel()));
                    h.oro.ganarOro(h.enemy.getRecompensa());
                    do {
                        if (h.pj.getXp() >= h.pj.getNivel()){
                            h.pj.setXp((int) (h.pj.getXp()-h.pj.getNivel()));
                            h.pj.setNivel(h.pj.getNivel()+1);
                            h.pj.setPuntosMejora((int) (h.pj.getPuntosMejora()+h.pj.getNivel()));
                        }
                    }while (h.pj.getXp() >= h.pj.getNivel());
                    attackButton.setEnabled(false);
                    continueButton.setEnabled(true);
                } else {
                    enemyHealthLabel.setText("HP: " + format1.format(enemyHealth));
                    if (daño == 0) {
                        logTextArea.append("\nFallaste" + "!\n");
                    } else {
                        logTextArea.append("\nAtaque realizado" + "!\n");
                        logTextArea.append("El oponente " + enemyLabel.getText() + " recibió " + format1.format(daño) + " de daño.\n");
                    }
                    opponentAttack();
                    if (FrameCombate.h.inventary.comprobarInventario(FrameCombate.h.reliq.aguaBendita)) {
                        playerHealth = FrameCombate.h.curas.curarCantidad(playerHealth, FrameCombate.h.vidaMax, FrameCombate.h.vidaMax * 0.1f);
                    }
                }
            }
        }
        if (e.getSource() == healthButton) {
            String health = (String) healthComboBox.getSelectedItem();
            int elecCuras = calculateHealth(health);
            switch (elecCuras) {
                case 1:
                    if (FrameCombate.h.curas.getCuras50().getCantidad()>0){
                        playerHealth=(h.curas.curarCantidad(playerHealth, h.vidaMax, 50));
                        FrameCombate.h.inventary.actualizarCantidad(FrameCombate.h.curas.getCuras50(), FrameCombate.h.curas.getCuras50().getCantidad()-1);
                    }
                    break;
                case 2:
                    if (FrameCombate.h.curas.getCuras100().getCantidad()>0){
                        playerHealth=(h.curas.curarCantidad(playerHealth, h.vidaMax, 100));
                        FrameCombate.h.inventary.actualizarCantidad(FrameCombate.h.curas.getCuras100(), FrameCombate.h.curas.getCuras100().getCantidad()-1);
                    }
                    break;
                case 3:
                    if (FrameCombate.h.curas.getCuras200().getCantidad()>0){
                        playerHealth=(h.curas.curarCantidad(playerHealth, h.vidaMax, 200));
                        FrameCombate.h.inventary.actualizarCantidad(FrameCombate.h.curas.getCuras200(), FrameCombate.h.curas.getCuras200().getCantidad()-1);
                    }
                    break;
            }
            playerHealthLabel.setText("HP: " + format1.format(playerHealth));
        }
        if (e.getSource() == seeButton) {
            String item = (String) inventoryComboBox.getSelectedItem();
            logTextArea.append(h.inventary.mostrarValores(item));
        }
        if (e.getSource() == useButton) {
            String item = (String) inventoryComboBox.getSelectedItem();
            if (h.inventary.getCantidad(item)==0){
                logTextArea.append("\nYa no te quedan.\n");
            }else {
                int uso = h.inventary.getUso(item);
                switch (uso) {
                    case 0:
                        logTextArea.append("\nEse item no se puede usar.\n");
                        break;
                    case 1:
                        h.arma.armaComun(h.arma.getNumArma(item));
                        h.pj.setNumarma(h.arma.getNumArma(item));
                        logTextArea.append("Ahora estas usando: "+item+".");
                        break;
                    case 2:
                        enemyHealth=h.hechizos.usarHechizo(item,enemyHealth);
                        playerDescription.setText(h.pj.toString()+"\nMana: "+h.pj.getMana());
                        logTextArea.append("\nUsas " + item + "\n");
                        enemyHealthLabel.setText("HP: " + format1.format(enemyHealth));

                        break;
                    case 3:
                        float vidaAntes= enemyHealth;
                        enemyHealth=h.dmgI.hacerDaño(item, 0,enemyHealth);
                        logTextArea.append("\nUsas " + item + " para hacer " + (vidaAntes-enemyHealth) + " de daño.\n");
                        enemyHealthLabel.setText("HP: " + format1.format(enemyHealth));
                        h.inventary.actualizarCantidad(h.dmgI.getDmgI(item), h.inventary.getCantidad(item)-1);
                        break;
                    case 4:
                        break;
                    case 5:
                        playerHealth = h.curas.curarCantidad(playerHealth, h.vidaMax, h.curas.getCantidad(item));
                        logTextArea.append("\nUsas " + item + " para curar " + h.curas.getCantidad(item) + " puntos de vida.\n");
                        playerHealthLabel.setText("HP: " + format1.format(playerHealth));
                        h.inventary.actualizarCantidad(h.curas.getCura(item), h.inventary.getCantidad(item)-1);
                        break;
                    case 6:
                        logTextArea.append("\nNo parece hacer nada.\n");
                        break;
                }
            }
        }
        if (e.getSource() == exitButton) {
            this.dispose();
            new FrameSalir();
        }
        if (e.getSource() == continueButton) {
            FrameCombate.h.pj.setVida(playerHealth);
            Main.h=FrameCombate.update(Main.h);
            if (nivelI<h.pj.getNivel()){
                FrameLevel.save(Main.h);
                new FrameLevel(frameMapa);
            }else{
                FrameMapa.ventana.setExtendedState(JFrame.NORMAL);
            }
            this.dispose();
        }

    }




    private int calculateDamage(String attack) {
        switch (attack) {
            case "Cuerpo (90% Daño x1)":
                return 1;
            case "Piernas (70% Daño x2)":
                return 2;
            case "Cabeza (50% Daño x3)":
                return 3;
            default:
                return 0;
        }
    }

    private int calculateHealth(String health) {
        switch (health) {
            case "Viales de 50.":
                return 1;
            case "Viales de 100.":
                return 2;
            case "Viales de 200.":
                return 3;
            default:
                return 0;
        }
    }

    private void opponentAttack() {
        float daño =h.combate.recibirAtaque(h.enemy.getAtaque());
        playerHealth -= daño;
        if (playerHealth <= 0) {
            playerHealth = 0;
            playerHealthLabel.setText("HP: " + format1.format(playerHealth));
            logTextArea.append("Has muerto\n");
            attackButton.setEnabled(false);
        } else {
            playerHealthLabel.setText("HP: " + format1.format(playerHealth));
            if (daño==0){
                logTextArea.append("El oponente " + enemyLabel.getText()+ " falló" + "!\n");
            }else {
                logTextArea.append("El oponente " + enemyLabel.getText()+ " realizó un ataque" + "!\n");
                logTextArea.append("Recibiste " + format1.format(daño) + " de daño.\n");
            }
        }
    }

}
