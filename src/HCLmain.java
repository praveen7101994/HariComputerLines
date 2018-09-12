
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Praveen
 */
public class HCLmain extends javax.swing.JFrame {

    /**
     * Creates new form HCLmain
     */
    private Connection connection = null;
    private PreparedStatement pst = null;
    private ResultSet resultSet = null;
    private byte[] personImg = null;
    private String filePath = null;
    
    public HCLmain() {
        initComponents();
        setIconme();
        center();
        connection = JavaDbConnect.dbConnect();
        updateStudentInfoTbl();
        updateStdntShrtInfo();
        currentDate();
        updateDoc();
    }
    
    public final void center() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(rootPaneCheckingEnabled);
    }
    
    public void close() {
        WindowEvent winClosingEvnt = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvnt);
    }
    
    private void updateStudentInfoTbl() {
        try {
            String sql = "select * from Student_info";
            pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();
            tblStudent_Info.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (SQLException ex) {
            Logger.getLogger(HCLmain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateStdntShrtInfo() {
        try {
            String sql = "select Student_Id, First_name from Student_info";
            pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();
            tblStdntShrtInfo.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (SQLException ex) {
            Logger.getLogger(HCLmain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private void updateDoc() {
        try {
            String sql = "select * from doc_Table";
            pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();
            tblStdntShrtInfo1.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (SQLException ex) {
            Logger.getLogger(HCLmain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void getValue() {
        try {
            txtStudentId.setText(resultSet.getString("Student_Id"));
            txtStudentFName.setText(resultSet.getString("First_name"));
            txtStudentLName.setText(resultSet.getString("Last_name"));
            txtStudentDept.setText(resultSet.getString("Department"));
            txtStudentSeries.setText(resultSet.getString("Series"));
            txtStudentAge.setText(resultSet.getString("Age"));
            txtStudentHeight.setText(resultSet.getString("Height"));
            txtStudentWeight.setText(resultSet.getString("Weight"));
            comboStudentGender.setSelectedItem(resultSet.getString("Gender"));
            txtStudentBlood.setText(resultSet.getString("Blood"));
            
            byte[] imageData = resultSet.getBytes("Photo");
            ImageIcon format = new ImageIcon(scaledImage(imageData, lableImage.getWidth(), lableImage.getHeight()));
            lableImage.setIcon(format);
        } catch (Exception e) {
            Logger.getLogger(HCLmain.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private Image scaledImage(byte[] img, int w, int h) {
        BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        try {
            Graphics2D g2 = resizedImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            //convert byte array back to buffered imge
            ByteArrayInputStream in = new ByteArrayInputStream(img);
            BufferedImage bImageFromConvert = ImageIO.read(in);
            g2.drawImage(bImageFromConvert, 0, 0, w, h, null);
            g2.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        return resizedImage;
    }
    
    private void currentDate() {
        Calendar cal = new GregorianCalendar();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONDAY);
        int year = cal.get(Calendar.YEAR);
        menuDate.setText("Current Date: " + day + "/" + month + "/" + year);
        menuDate.setForeground(Color.blue);
        
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR);
        menuTime.setText("Current Time: " + hour + "/" + minute + "/" + second);
        menuTime.setForeground(Color.blue);
    }

    // get screenshot of selected components
    private static BufferedImage getScreenShot(Component com) {
        BufferedImage image = new BufferedImage(com.getWidth(), com.getHeight(), BufferedImage.TYPE_INT_RGB);
        com.paint(image.getGraphics());
        return image;
    }

    // save the image of selected component
    private static void saveScreenShot(Component com, String fileName) throws Exception {
        BufferedImage img = getScreenShot(com);
        ImageIO.write(img, "png", new File(fileName));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudent_Info = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblStdntShrtInfo1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtFrom = new javax.swing.JTextField();
        txtTo = new javax.swing.JTextField();
        txtSub = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMsg = new javax.swing.JTextArea();
        txtAttach = new javax.swing.JTextField();
        txtAttachName = new javax.swing.JTextField();
        btnAttach = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        btnSendMail = new javax.swing.JButton();
        txtPass = new javax.swing.JPasswordField();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        panelStudentInfo = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtStudentId = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtStudentFName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtStudentLName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtStudentDept = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtStudentSeries = new javax.swing.JTextField();
        txtStudentAge = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtStudentHeight = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtStudentWeight = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtStudentBlood = new javax.swing.JTextField();
        comboStudentGender = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStdntShrtInfo = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lableImage = new javax.swing.JLabel();
        txtImageUpload = new javax.swing.JTextField();
        btnImageUpload = new javax.swing.JButton();
        btnimgSave = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mItClose = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        mItOffHelp = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        mItWebHelp = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuDate = new javax.swing.JMenu();
        menuTime = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        jButton1.setText("Sign-Out");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/web_help.png"))); // NOI18N
        jButton2.setText("jButton2");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jPanel1.setBackground(new java.awt.Color(0, 102, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Action Panel", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(0, 255, 255))); // NOI18N

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        tblStudent_Info.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblStudent_Info.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblStudent_Info.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudent_InfoMouseClicked(evt);
            }
        });
        tblStudent_Info.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblStudent_InfoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblStudent_Info);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 343, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Data Table", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1225, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Chart", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1225, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Staticstics", jPanel4);

        jPanel13.setBackground(new java.awt.Color(204, 255, 204));

        tblStdntShrtInfo1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblStdntShrtInfo1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblStdntShrtInfo1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblStdntShrtInfo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStdntShrtInfo1MouseClicked(evt);
            }
        });
        tblStdntShrtInfo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblStdntShrtInfo1KeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblStdntShrtInfo1);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Attach20.png"))); // NOI18N
        jButton4.setText("Attach");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add20.png"))); // NOI18N
        jButton5.setText("Add");

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete20.png"))); // NOI18N
        jButton6.setText("Delete");

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear22.png"))); // NOI18N
        jButton7.setText("Clear");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 255));
        jLabel17.setText("Doc Id");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 255));
        jLabel18.setText("Student Id");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 255));
        jLabel19.setText("Doc Name");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(261, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7)
                    .addComponent(jLabel19))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Document", jPanel5);

        jPanel12.setBackground(new java.awt.Color(153, 204, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 255));
        jLabel9.setText("From");
        jPanel12.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 13, 59, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 255));
        jLabel13.setText("Password");
        jPanel12.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 51, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 255));
        jLabel14.setText("To");
        jPanel12.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 89, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 255));
        jLabel15.setText("Subject");
        jPanel12.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 127, -1, -1));
        jPanel12.add(txtFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 11, 188, -1));
        jPanel12.add(txtTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 87, 188, -1));
        jPanel12.add(txtSub, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 125, 188, -1));

        txtMsg.setColumns(20);
        txtMsg.setRows(5);
        jScrollPane3.setViewportView(txtMsg);

        jPanel12.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(391, 11, 399, 134));
        jPanel12.add(txtAttach, new org.netbeans.lib.awtextra.AbsoluteConstraints(391, 156, 259, -1));
        jPanel12.add(txtAttachName, new org.netbeans.lib.awtextra.AbsoluteConstraints(391, 187, 259, -1));

        btnAttach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Attach20.png"))); // NOI18N
        btnAttach.setText("Attach");
        btnAttach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttachActionPerformed(evt);
            }
        });
        jPanel12.add(btnAttach, new org.netbeans.lib.awtextra.AbsoluteConstraints(668, 156, 122, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 51, 255));
        jLabel16.setText("Attachment Name");
        jPanel12.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(668, 187, 122, -1));

        btnSendMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/send_mail32.png"))); // NOI18N
        btnSendMail.setText("Send Mail");
        btnSendMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMailActionPerformed(evt);
            }
        });
        jPanel12.add(btnSendMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(391, 213, 399, 51));
        jPanel12.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 49, 188, -1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("E-mail", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 153));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Student Information System");

        txtSearch.setText("Search...");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search 24.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Commands", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 153))); // NOI18N

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add20.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit20.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete20.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear22.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(17, 17, 17)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelStudentInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Info", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 153))); // NOI18N

        jLabel11.setText("Student Id");

        txtStudentId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentIdActionPerformed(evt);
            }
        });

        jLabel12.setText("First Name");

        txtStudentFName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentFNameActionPerformed(evt);
            }
        });

        jLabel1.setText("Last Name");

        jLabel2.setText("Department");

        jLabel3.setText("Series");

        txtStudentAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentAgeActionPerformed(evt);
            }
        });

        jLabel4.setText("Age");

        jLabel5.setText("Height");

        txtStudentHeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentHeightActionPerformed(evt);
            }
        });

        jLabel6.setText("Weight");

        jLabel7.setText("Gender");

        jLabel8.setText("Blood");

        comboStudentGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "  ", "Male", "Female", "Other" }));
        comboStudentGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboStudentGenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelStudentInfoLayout = new javax.swing.GroupLayout(panelStudentInfo);
        panelStudentInfo.setLayout(panelStudentInfoLayout);
        panelStudentInfoLayout.setHorizontalGroup(
            panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStudentDept)
                    .addComponent(txtStudentSeries)
                    .addComponent(txtStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStudentFName)
                    .addComponent(txtStudentLName))
                .addGap(65, 65, 65)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(31, 31, 31)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtStudentAge)
                    .addComponent(txtStudentHeight)
                    .addComponent(txtStudentWeight)
                    .addComponent(txtStudentBlood)
                    .addComponent(comboStudentGender, 0, 98, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelStudentInfoLayout.setVerticalGroup(
            panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStudentAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStudentFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel5)
                    .addComponent(txtStudentHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtStudentLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtStudentWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboStudentGender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtStudentDept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addGroup(panelStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtStudentSeries, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtStudentBlood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(0, 102, 153));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblStdntShrtInfo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblStdntShrtInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblStdntShrtInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStdntShrtInfoMouseClicked(evt);
            }
        });
        tblStdntShrtInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblStdntShrtInfoKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblStdntShrtInfo);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lableImage, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lableImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        txtImageUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImageUploadActionPerformed(evt);
            }
        });

        btnImageUpload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnImageUpload.setText("Upload");
        btnImageUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImageUploadActionPerformed(evt);
            }
        });

        btnimgSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnimgSave.setText("save");
        btnimgSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimgSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btnImageUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnimgSave))
                    .addComponent(txtImageUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtImageUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImageUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnimgSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText(" File");

        mItClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        mItClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        mItClose.setText("Close");
        mItClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItCloseActionPerformed(evt);
            }
        });
        jMenu1.add(mItClose);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(" Edit");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/snip.png"))); // NOI18N
        jMenuItem3.setText("Snip");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        mItOffHelp.setText(" Help");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/help16.png"))); // NOI18N
        jMenuItem2.setText("Offline Help");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mItOffHelp.add(jMenuItem2);

        mItWebHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/web_help.png"))); // NOI18N
        mItWebHelp.setText("Web Help");
        mItWebHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItWebHelpActionPerformed(evt);
            }
        });
        mItOffHelp.add(mItWebHelp);

        jMenuBar1.add(mItOffHelp);

        jMenu4.setText(" About");
        jMenuBar1.add(jMenu4);

        menuDate.setText(" Date");
        jMenuBar1.add(menuDate);

        menuTime.setText("Time");
        jMenuBar1.add(menuTime);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelStudentInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelStudentInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mItCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItCloseActionPerformed
        // TODO add your handling code here:

        try {
            close();
            Login obj = new Login();
            obj.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        } finally {
            try {
                //resultSet.close();
                //pst.close();
                connection.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }
        }
    }//GEN-LAST:event_mItCloseActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            close();
            Login obj = new Login();
            obj.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e+ "error");
        } finally {
            try {
                //resultSet.close();
                //pst.close();
                connection.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtStudentFNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentFNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentFNameActionPerformed

    private void comboStudentGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboStudentGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboStudentGenderActionPerformed

    private void txtStudentHeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentHeightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentHeightActionPerformed

    private void tblStdntShrtInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStdntShrtInfoMouseClicked
        try {
            // TODO add your handling code here:
            int row = tblStdntShrtInfo.getSelectedRow();
            String tableClick = (tblStdntShrtInfo.getModel().getValueAt(row, 0).toString());
            String sql = "select * from Student_info where Student_Id = '" + tableClick + "' ";
            pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
                getValue();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_tblStdntShrtInfoMouseClicked

    private void txtStudentIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentIdActionPerformed

    private void tblStdntShrtInfoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblStdntShrtInfoKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            try {
                // TODO add your handling code here:
                int row = tblStdntShrtInfo.getSelectedRow();
                String tableClick = (tblStdntShrtInfo.getModel().getValueAt(row, 0).toString());
                String sql = "select * from Student_info where Student_Id = '" + tableClick + "' ";
                pst = connection.prepareStatement(sql);
                resultSet = pst.executeQuery();
                if (resultSet.next()) {
                    getValue();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex);
            }
        }
    }//GEN-LAST:event_tblStdntShrtInfoKeyReleased

    private void tblStudent_InfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudent_InfoMouseClicked
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            int row = tblStudent_Info.getSelectedRow();
            String tableClick = (tblStudent_Info.getModel().getValueAt(row, 0).toString());
            String sql = "select * from Student_info where Student_Id = '" + tableClick + "' ";
            pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
                getValue();
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_tblStudent_InfoMouseClicked

    private void tblStudent_InfoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblStudent_InfoKeyReleased
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            int row = tblStudent_Info.getSelectedRow();
            String tableClick = (tblStudent_Info.getModel().getValueAt(row, 0).toString());
            String sql = "select * from Student_info where Student_Id = '" + tableClick + "' ";
            pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
                getValue();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_tblStudent_InfoKeyReleased

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Files\\PraveenPatel.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error opening file!");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Files\\PraveenPatel.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error opening file!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void mItWebHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItWebHelpActionPerformed
        try {
            // TODO add your handling code here:
            String url = "https://www.haricomputerlines.com";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_mItWebHelpActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int p = JOptionPane.showConfirmDialog(rootPane, "Do you really want to delete?", "Delete", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            String sql = "delete from Student_info where Student_Id=?";
            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, txtStudentId.getText());
                pst.execute();
                JOptionPane.showMessageDialog(rootPane, "Deleted!");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }
            updateStdntShrtInfo();
            updateStudentInfoTbl();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String sql = "select * from Student_info where First_name=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtSearch.getText());
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
                getValue();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        
        String sqlId = "select * from Student_info where Student_Id=?";
        try {
            pst = connection.prepareStatement(sqlId);
            pst.setString(1, txtSearch.getText());
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
                getValue();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        txtStudentId.setText(null);
        txtStudentFName.setText(null);
        txtStudentLName.setText(null);
        txtStudentDept.setText(null);
        txtStudentSeries.setText(null);
        txtStudentAge.setText(null);
        txtStudentHeight.setText(null);
        txtStudentWeight.setText(null);
        comboStudentGender.setSelectedItem("  ");
        txtStudentBlood.setText(null);

    }//GEN-LAST:event_btnClearActionPerformed

    private void txtStudentAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentAgeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentAgeActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        String sql = "insert into Student_info (Student_Id, First_name, Last_name, Department, Series, Age, Height, Weight, Gender, Blood) values(?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentId.getText());
            pst.setString(2, txtStudentFName.getText());
            pst.setString(3, txtStudentLName.getText());
            pst.setString(4, txtStudentDept.getText());
            pst.setString(5, txtStudentSeries.getText());
            pst.setString(6, txtStudentAge.getText());
            pst.setString(7, txtStudentHeight.getText());
            pst.setString(8, txtStudentWeight.getText());
            pst.setString(9, (String) comboStudentGender.getSelectedItem());
            pst.setString(10, txtStudentBlood.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, "Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        updateStdntShrtInfo();
        updateStudentInfoTbl();
    }//GEN-LAST:event_btnAddActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        try {
            saveScreenShot(panelStudentInfo, "Panel Img.png");
            JOptionPane.showMessageDialog(rootPane, "Image is captured");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void txtImageUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImageUploadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImageUploadActionPerformed

    private void btnImageUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImageUploadActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        
        File f = chooser.getSelectedFile();
        String fileName = f.getAbsolutePath();
        txtImageUpload.setText(fileName);
        try {
            FileInputStream fIS = new FileInputStream(f);
            ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fIS.read(buf)) != -1;) {
                bAOS.write(buf, 0, readNum);
            }
            personImg = bAOS.toByteArray();
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnImageUploadActionPerformed

    private void btnimgSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimgSaveActionPerformed
        // TODO add your handling code here:
        String sql = "update Student_info set Photo = ? where Student_Id = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setBytes(1, personImg);
            pst.setString(2, txtStudentId.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, "Image Saved.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_btnimgSaveActionPerformed

    private void btnSendMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMailActionPerformed
        // TODO add your handling code here:
        final String From = txtFrom.getText();
        final String password = txtPass.getText();
        String To = txtTo.getText();
        String Subject = txtSub.getText();
        String txtMessage = txtMsg.getText();
        Properties pros = new Properties();
        pros.put("mail.smtp.host", "smtp.gmail.com");
        pros.put("mail.smtp.socketFactory.port", "465");//SSL protocol port no is 465
        pros.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        pros.put("mail.smtp.auth", "true");
        pros.put("mail.smtp.port", "465");
        
        
        Session session = Session.getDefaultInstance(pros,
                new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(From, password);
                    }
                });
        try {
            //message header
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(From));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To));
            message.setSubject(Subject);
            
            //code for set text message
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(txtMessage);
            Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(messageBodyPart);
            
            //code for attach file
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(txtAttach.getText());
            multiPart.addBodyPart(messageBodyPart);
            
            //send message
            message.setContent(multiPart);
            Transport.send(message);
            
            JOptionPane.showMessageDialog(rootPane, "Message sent");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        
        
    }//GEN-LAST:event_btnSendMailActionPerformed

    private void btnAttachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttachActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(rootPane);

        File f = chooser.getSelectedFile();
        filePath = f.getAbsolutePath();

        txtAttach.setText(filePath);
        txtAttachName.setText(filePath);
    }//GEN-LAST:event_btnAttachActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        String sql = "update Student_info set First_name = ?, Last_name = ?, Department = ?, Series = ?, Age = ?, Height = ?, Weight = ?, Gender = ?, Blood = ? where Student_Id = ?";
        try{
            pst = connection.prepareStatement(sql);
            pst.setString(1, txtStudentFName.getText());
            pst.setString(2, txtStudentLName.getText());
            pst.setString(3, txtStudentDept.getText());
            pst.setString(4, txtStudentSeries.getText());
            pst.setString(5, txtStudentAge.getText());
            pst.setString(6, txtStudentHeight.getText());
            pst.setString(7, txtStudentWeight.getText());
            pst.setString(8, (String) comboStudentGender.getSelectedItem());
            pst.setString(9, txtStudentBlood.getText());
            pst.setString(10, txtStudentId.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, "Saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        updateStdntShrtInfo();
        updateStudentInfoTbl();
    }//GEN-LAST:event_btnEditActionPerformed

    private void tblStdntShrtInfo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStdntShrtInfo1MouseClicked
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            int row = tblStdntShrtInfo1.getSelectedRow();
            String tableClick = (tblStdntShrtInfo1.getModel().getValueAt(row, 0).toString());
            String sql = "select * from Doc_Table where Doc_Id = '" + tableClick + "' ";
            pst = connection.prepareStatement(sql);
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
            jTextField2.setText(resultSet.getString("Doc_Id"));
            jTextField3.setText(resultSet.getString("Student_Id"));
            jTextField4.setText(resultSet.getString("Doc_Name"));
            jTextField1.setText(resultSet.getString("path"));
            
            
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        try {
            int row = tblStdntShrtInfo1.getSelectedRow();
            String tableClick = (tblStdntShrtInfo1.getModel().getValueAt(row, 3).toString());
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + tableClick);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_tblStdntShrtInfo1MouseClicked

    private void tblStdntShrtInfo1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblStdntShrtInfo1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblStdntShrtInfo1KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HCLmain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HCLmain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HCLmain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HCLmain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HCLmain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAttach;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnImageUpload;
    private javax.swing.JButton btnSendMail;
    private javax.swing.JButton btnimgSave;
    private javax.swing.JButton buttonReport;
    private javax.swing.JButton buttonReport1;
    private javax.swing.JButton buttonReport2;
    private javax.swing.JComboBox<String> comboStudentGender;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lableImage;
    private javax.swing.JMenuItem mItClose;
    private javax.swing.JMenu mItOffHelp;
    private javax.swing.JMenuItem mItWebHelp;
    private javax.swing.JMenu menuDate;
    private javax.swing.JMenu menuTime;
    private javax.swing.JPanel panelStudentInfo;
    private javax.swing.JSlider sliderAge;
    private javax.swing.JSlider sliderAge1;
    private javax.swing.JSlider sliderAge2;
    private javax.swing.JSlider sliderAge3;
    private javax.swing.JSlider sliderHeight;
    private javax.swing.JSlider sliderHeight1;
    private javax.swing.JSlider sliderHeight2;
    private javax.swing.JSlider sliderHeight3;
    private javax.swing.JSlider sliderWeight;
    private javax.swing.JSlider sliderWeight1;
    private javax.swing.JSlider sliderWeight2;
    private javax.swing.JSlider sliderWeight3;
    private javax.swing.JTextArea tAreaComment;
    private javax.swing.JTextArea tAreaComment1;
    private javax.swing.JTextArea tAreaComment2;
    private javax.swing.JTable tblStdntShrtInfo;
    private javax.swing.JTable tblStdntShrtInfo1;
    private javax.swing.JTable tblStudent_Info;
    private javax.swing.JTextField txtAttach;
    private javax.swing.JTextField txtAttachName;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextField txtImageUpload;
    private javax.swing.JTextArea txtMsg;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStudentAge;
    private javax.swing.JTextField txtStudentBlood;
    private javax.swing.JTextField txtStudentDept;
    private javax.swing.JTextField txtStudentFName;
    private javax.swing.JTextField txtStudentHeight;
    private javax.swing.JTextField txtStudentId;
    private javax.swing.JTextField txtStudentLName;
    private javax.swing.JTextField txtStudentSeries;
    private javax.swing.JTextField txtStudentWeight;
    private javax.swing.JTextField txtSub;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables

    private void setIconme() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/mypic.png")));
    }
}
