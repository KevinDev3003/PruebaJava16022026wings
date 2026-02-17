
package prueba.wings.pruebaJavaWings.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prueba.wings.pruebaJavaWings.model.Familia;
import prueba.wings.pruebaJavaWings.model.Producto;
import prueba.wings.pruebaJavaWings.repository.FamiliaRepository;
import prueba.wings.pruebaJavaWings.repository.ProductoRepository;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final FamiliaRepository familiaRepository;

    public ProductoController(ProductoRepository productoRepository, FamiliaRepository familiaRepository) {
        this.productoRepository = productoRepository;
        this.familiaRepository = familiaRepository;
    }

    private boolean noLogueado(HttpSession session) {
        return session.getAttribute("user") == null;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String msg,
                         Model model,
                         HttpSession session) {
        if (noLogueado(session)) return "redirect:/login";

        if (msg != null && !msg.isBlank()) {
            model.addAttribute("msg", msg);
        }

        model.addAttribute("productos", productoRepository.findAll());
        return "productos-list";
    }

    @GetMapping("/new")
    public String nuevoForm(Model model, HttpSession session) {
        if (noLogueado(session)) return "redirect:/login";

        model.addAttribute("producto", new Producto());
        model.addAttribute("familias", familiaRepository.findAll());
        return "producto-form";
    }

    @PostMapping
    public String crear(@ModelAttribute Producto producto,
                        @RequestParam(required = false) Integer familiaId,
                        @RequestParam(required = false) String nuevaFamiliaNombre,
                        HttpSession session,
                        Model model) {

        if (noLogueado(session)) return "redirect:/login";

        //Validar que el ID lo haya puesto el usuario
        if (producto.getIdProducto() == null) {
            model.addAttribute("error", "Debe ingresar el código (ID) del producto.");
            model.addAttribute("producto", producto);
            model.addAttribute("familias", familiaRepository.findAll());
            return "producto-form";
        }

        //Evitar IDs repetidos
        if (productoRepository.existsById(producto.getIdProducto())) {
            model.addAttribute("error", "Ese código (ID) ya existe. Use otro.");
            model.addAttribute("producto", producto);
            model.addAttribute("familias", familiaRepository.findAll());
            return "producto-form";
        }

        boolean seleccionoFamilia = (familiaId != null);
        boolean escribioNueva = (nuevaFamiliaNombre != null && !nuevaFamiliaNombre.trim().isEmpty());

        // si eligió una, no puede crear otra
        if (seleccionoFamilia && escribioNueva) {
            model.addAttribute("error", "Si selecciona una familia existente, no puede agregar una nueva.");
            model.addAttribute("producto", producto);
            model.addAttribute("familias", familiaRepository.findAll());
            return "producto-form";
        }

        // debe escoger una o escribir una nueva
        if (!seleccionoFamilia && !escribioNueva) {
            model.addAttribute("error", "Debe seleccionar una familia o escribir una nueva.");
            model.addAttribute("producto", producto);
            model.addAttribute("familias", familiaRepository.findAll());
            return "producto-form";
        }

        //nueva o existente
        Familia familia;
        if (escribioNueva) {
            familia = new Familia();
            familia.setNombreFamilia(nuevaFamiliaNombre.trim());
            familia = familiaRepository.save(familia);
        } else {
            familia = familiaRepository.findById(familiaId).orElseThrow();
        }

        producto.setFamilia(familia);
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    // editaaaaaa por selección (radio button) + fallback si no viene idProducto
    @GetMapping("/edit")
    public String editarSeleccion(@RequestParam(required = false) Integer idProducto,
                                  Model model,
                                  HttpSession session) {
        if (noLogueado(session)) return "redirect:/login";

        if (idProducto == null) {
            return "redirect:/productos?msg=Debe%20seleccionar%20un%20producto%20para%20editar";
        }

        var producto = productoRepository.findById(idProducto).orElse(null);
        if (producto == null) {
            return "redirect:/productos?msg=El%20producto%20seleccionado%20no%20existe";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("familias", familiaRepository.findAll());
        return "producto-form";
    }

    // ACTUALIZAR (form de editar)
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Integer id,
                             @ModelAttribute Producto producto,
                             @RequestParam(required = false) Integer familiaId,
                             @RequestParam(required = false) String nuevaFamiliaNombre,
                             HttpSession session,
                             Model model) {

        if (noLogueado(session)) return "redirect:/login";

        // Asegurar ID correcto (no cambia)
        producto.setIdProducto(id);

        boolean seleccionoFamilia = (familiaId != null);
        boolean escribioNueva = (nuevaFamiliaNombre != null && !nuevaFamiliaNombre.trim().isEmpty());

        if (seleccionoFamilia && escribioNueva) {
            model.addAttribute("error", "Si selecciona una familia existente, no puede agregar una nueva.");
            model.addAttribute("producto", producto);
            model.addAttribute("familias", familiaRepository.findAll());
            return "producto-form";
        }

        if (!seleccionoFamilia && !escribioNueva) {
            model.addAttribute("error", "Debe seleccionar una familia o escribir una nueva.");
            model.addAttribute("producto", producto);
            model.addAttribute("familias", familiaRepository.findAll());
            return "producto-form";
        }

        Familia familia;
        if (escribioNueva) {
            familia = new Familia();
            familia.setNombreFamilia(nuevaFamiliaNombre.trim());
            familia = familiaRepository.save(familia);
        } else {
            familia = familiaRepository.findById(familiaId).orElseThrow();
        }

        producto.setFamilia(familia);
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    // borarr por selección + fallback si no viene idProducto
    @PostMapping("/delete")
    public String borrarSeleccion(@RequestParam(required = false) Integer idProducto,
                                  HttpSession session) {
        if (noLogueado(session)) return "redirect:/login";

        if (idProducto == null) {
            return "redirect:/productos?msg=Debe%20seleccionar%20un%20producto%20para%20borrar";
        }

        if (!productoRepository.existsById(idProducto)) {
            return "redirect:/productos?msg=El%20producto%20seleccionado%20no%20existe";
        }

        productoRepository.deleteById(idProducto);
        return "redirect:/productos";
    }
}
