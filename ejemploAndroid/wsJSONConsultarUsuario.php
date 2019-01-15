<?PHP
$hostname_localhost ="localhost";
$database_localhost ="bd_usuario";
$username_localhost ="root";
$password_localhost ="";

$json=array();

	if(isset($_GET["documento"])){
		$documento=$_GET["documento"];
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

		$consulta="select documento,nombre,profesion from usuario where documento= '{$documento}'";
		$resultado=mysqli_query($conexion,$consulta);
		
			
		if($registro=mysqli_fetch_array($resultado)){
			$miarray = array();
			
			$miarray[] = $registro[0];
			$miarray[] = $registro[1];
			$miarray[] = $registro[2];
			
			$json['usuario'][]="Nombre: ".$registro[1]."    Profesion: ".$registro[2];
			
			//echo $registro[0];
			//echo "<br>";
			//echo $registro['documento'].' - '.$registro['nombre'].' - '.$registro['profesion'].'<br/>';
		}else{
			$resultar["documento"]=0;
			$resultar["nombre"]='no registra';
			$resultar["profesion"]='no registra';
			$json['usuario'][]=$resultar;
		}
		
		mysqli_close($conexion);
		echo json_encode($json);
	}
	else{
		$resultar["success"]=0;
		$resultar["message"]='Ws no Retorna';
		$json['usuario'][]=$resultar;
		echo json_encode($json);
	}
?>