package ar.edu.unlu.envio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
//import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Component;

public class VentanaCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtConsulta;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static Cliente c;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Cliente c2= new Cliente();
					VentanaCliente frame = new VentanaCliente(c2);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param c2 
	 */
	public VentanaCliente(Cliente c2) {
		this.c=c2;
		final DefaultListModel modeloLista = new DefaultListModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 821, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[][104.00][][grow][][][][grow]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "CONSULTA SQL", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, "cell 1 1,grow");
		panel.setLayout(new MigLayout("", "[73px][91px][87px]", "[25px][][][][][][]"));
		
		JRadioButton rdbtnFirebird = new JRadioButton("Firebird");
		rdbtnFirebird.setSelected(true);
		buttonGroup.add(rdbtnFirebird);
		panel.add(rdbtnFirebird, "cell 0 0,alignx left,aligny top");
		
		
		final JRadioButton rdbtnPostgresql = new JRadioButton("PosgreSQL");
		buttonGroup.add(rdbtnPostgresql);
		panel.add(rdbtnPostgresql, "cell 0 1,alignx left,aligny top");
		if (rdbtnFirebird.isSelected()) {
			rdbtnPostgresql.setSelected(false);
		}
		if (rdbtnPostgresql.isSelected()) {
			rdbtnFirebird.setSelected(false);
		}
		JLabel lblIngreseSuConsulta = new JLabel("Ingrese su consulta SQL:");
		panel.add(lblIngreseSuConsulta, "cell 0 2");
		
		txtConsulta = new JTextField();
		panel.add(txtConsulta, "cell 0 4 3 1,growx");
		txtConsulta.setColumns(10);
		
		JButton btnConsultar = new JButton("Consultar");
		
		panel.add(btnConsultar, "cell 1 6,alignx left,aligny top");
		
		JPanel panel_1 = new JPanel();
		panel_1.setAutoscrolls(true);
		panel_1.setBorder(new TitledBorder(null, "Resultado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1, "cell 1 3 1 5,grow");
		panel_1.setLayout(new MigLayout("", "[697px]", "[214.00px]"));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "cell 0 0,grow");
		
		JList listResultado = new JList();
		scrollPane.setViewportView(listResultado);
		listResultado.setModel(modeloLista);
		JScrollBar scrollBar = new JScrollBar();
		//panel_1.add(scrollBar, "cell 0 0");
	
		btnConsultar.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
				int n;
				n=1;
					if (rdbtnPostgresql.isSelected()) {
						n=2;
					}else
					{n=1;};
				modeloLista.clear();
				c.crearConexion();
				c.enviarTexto(String.valueOf(n),txtConsulta.getText());
				c.crearConexion();
				c.recibir();
				DefaultListModel listas= leerXML();
				for (int i=1;i<listas.getSize();i++) {
					modeloLista.addElement(listas.get(i));
				}
			}

		});
		
	}
	
	private DefaultListModel leerXML() {
		// TODO Auto-generated method stub
		 DefaultListModel model= new DefaultListModel();
		 File file= new File("C:\\Users\\feder\\query.xml");
		 String st="";
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		 DocumentBuilder documentBuilder;
		 try {
			 documentBuilder = dbf.newDocumentBuilder();   
			 Document document = documentBuilder.parse(file);
			
			 NodeList columnas = document.getElementsByTagName("cols");
			 for (int temp = 0; temp<columnas.getLength(); temp++) {
				 Node nodo = columnas.item(temp);
				 
				 
				 if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					 Element element = (Element) nodo;
					 st=(element.getElementsByTagName("colname1").item(0).getTextContent()+"                  ");
					 st=st+(element.getElementsByTagName("colname2").item(0).getTextContent()+"                  ");
					 st=st+ (element.getElementsByTagName("colname3").item(0).getTextContent()+"                   ");
					 st=st+(element.getElementsByTagName("colname4").item(0).getTextContent()+"                    ");  
					 model.addElement("");
					 model.addElement(st);
				 }
			 }
		
			 NodeList filas = document.getElementsByTagName("row");
			 for (int i = 0; i<filas.getLength(); i++) {
				 Node nodo2 = filas.item(i);
				// System.out.println("Elemento:" + nodo2.getNodeName());
				 if (nodo2.getNodeType() == Node.ELEMENT_NODE) {  
					 Element elemento = (Element) nodo2;
					 
					 st="";
					 for (int x=1;x<=4;x++) {
						 int num=30;
						 String ss=elemento.getElementsByTagName("col"+String.valueOf(x)).item(0).getTextContent();
						 st=st+ss;
						 num=num-(ss).length();
						 while (num>0) {
							 st=st +" ";
							 num=num-1;
						 }
					 }
					 model.addElement(st);
					
				 }
			 }
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addElement("HUBO UN ERROR");
			}catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addElement("HUBO UN ERROR");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addElement("HUBO UN ERROR");
			}
	    
		return model;
	}
}
