function eliminar(id) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger"
        },
        buttonsStyling: false
    });
    swalWithBootstrapButtons.fire({
        title: "¿Estás seguro de eliminarlo?",
        text: "No vas a poder deshacerlo",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Si, borralo",
        cancelButtonText: "No, cancelalo",
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            $ajax({
               url:"/eliminar/"+id,
               succes: function(res){
                    console.log(res);
               }
            });
            swalWithBootstrapButtons.fire({
                title: "Eliminado",
                text: "El dato ha sido eliminado",
                icon: "success"
            }).then((result)=>{
                if (result.isConfirmed) {
                    location.href="/gestionUsuario";
                }
            });
        } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
                ) {
            swalWithBootstrapButtons.fire({
                title: "Cancelado",
                text: "El archivo esta seguro",
                icon: "error"
            });
        }
    }
    );
}