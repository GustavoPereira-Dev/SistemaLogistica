package com.example.frota.solicitacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.frota.caixa.CaixaService;
import com.example.frota.caminhao.CaminhaoService;
import com.example.frota.produto.ProdutoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/solicitacao")
public class SolicitacaoController {
	
	@Autowired
	private SolicitacaoService solicitacaoService;
	
	@Autowired
    private SolicitacaoMapper solicitacaoMapper;
	
	@Autowired
	private CaixaService caixaService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CaminhaoService caminhaoService;
	
	@GetMapping                 
	public String carregaPaginaFormulario ( Model model){ 
		//devolver DTO
		model.addAttribute("lista", solicitacaoService.procurarTodos());
	    return "solicitacao/listagem";              
	} 
	
	@GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Long id, Model model) {
		AtualizacaoSolicitacao dto;
        if (id != null) {
            //edição: Carrega dados existentes
            Solicitacao solicitacao = solicitacaoService.procurarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado"));
            dto = solicitacaoMapper.toAtualizacaoDto(solicitacao);
        } else {
            dto = new AtualizacaoSolicitacao(null, null, null, null, null, null, null, null, null, null, null);
        }
        model.addAttribute("solicitacao", dto);
        model.addAttribute("caixas", caixaService.procurarTodos());
        model.addAttribute("produtos", produtoService.procurarTodos());
        model.addAttribute("caminhoes", caminhaoService.procurarTodos());
        return "solicitacao/formulario";
    }
	
	@GetMapping ("/formulario/{id}")    
	public String carregaPaginaFormulario (@PathVariable("id") Long id, Model model,
			RedirectAttributes redirectAttributes) {
		AtualizacaoSolicitacao dto;
		try {
	        model.addAttribute("caixas", caixaService.procurarTodos());
	        model.addAttribute("produtos", produtoService.procurarTodos());
	        model.addAttribute("caminhoes", caminhaoService.procurarTodos());
	        
			if(id != null) {
				Solicitacao solicitacao = solicitacaoService.procurarPorId(id)
						.orElseThrow(() -> new EntityNotFoundException("Caminhao não encontrado"));
				model.addAttribute("solicitacoes", solicitacaoService.procurarTodos());

				dto = solicitacaoMapper.toAtualizacaoDto(solicitacao);
				model.addAttribute("solicitacao", dto);
			}
			return "solicitacao/formulario";
		} catch (EntityNotFoundException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/solicitacao";
		}
	}
	

	@PostMapping("/salvar")
    public String salvar(@ModelAttribute("solicitacao") @Valid AtualizacaoSolicitacao dto,
                        BindingResult result,
                        RedirectAttributes redirectAttributes,
                        Model model) {
		if (result.hasErrors()) {
	        model.addAttribute("solicitacoes", solicitacaoService.procurarTodos());
	        return "solicitacao/formulario";
	    }
	    try {
	        Solicitacao solicitacaoSalva = solicitacaoService.salvarOuAtualizar(dto);
	        String mensagem = dto.id() != null 
	            ? "Caminhão '" + solicitacaoSalva.getId() + "' atualizado com sucesso!"
	            : "Caminhão '" + solicitacaoSalva.getId() + "' criado com sucesso!";
	        redirectAttributes.addFlashAttribute("message", mensagem);
	        return "redirect:/solicitacao";
	    } catch (EntityNotFoundException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/solicitacao/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
	    } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	@GetMapping("/delete/{id}")
	@Transactional
	public String deleteTutorial(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		try {
			solicitacaoService.apagarPorId(id);
			redirectAttributes.addFlashAttribute("message", "O caminhao " + id + " foi apagado!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/solicitacao";
	}
	
}